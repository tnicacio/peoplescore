package com.tnicacio.peoplescore.event;

import com.tnicacio.peoplescore.exception.EventException;
import com.tnicacio.peoplescore.exception.factory.ExceptionFactory;
import com.tnicacio.peoplescore.score.dto.ScoreDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.function.Consumer;

@Component
public class AppEvents {

    private static final Logger logger = LoggerFactory.getLogger(AppEvents.class);

    @Value("${client.super.username}")
    private String defaultUsername;
    @Value("${client.super.password}")
    private String defaultPassword;
    @Value("${security.oauth2.client.client-id}")
    private String clientId;
    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;

    private final WebClient webClient;
    private final ExceptionFactory exceptionFactory;

    public AppEvents(ExceptionFactory exceptionFactory) {
        this.exceptionFactory = exceptionFactory;
        webClient = WebClient.create("http://localhost:8080");
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startApp() {
        Consumer<String> postScoreConsumer = (String token) -> Flux.fromIterable(getDefaultScores())
                .flatMap(score -> postScore(score, token)).subscribe();
        login().doOnSuccess(postScoreConsumer).subscribe();
    }

    private List<ScoreDTO> getDefaultScores() {
        ScoreDTO insufficient = ScoreDTO.builder().description("Insuficiente").initialScore(0L).finalScore(200L).build();
        ScoreDTO unacceptable = ScoreDTO.builder().description("Inaceitável").initialScore(201L).finalScore(500L).build();
        ScoreDTO acceptable = ScoreDTO.builder().description("Aceitável").initialScore(501L).finalScore(700L).build();
        ScoreDTO recommendable = ScoreDTO.builder().description("Recomendável").initialScore(701L).finalScore(1000L).build();
        return List.of(insufficient, unacceptable, acceptable, recommendable);
    }

    private Mono<ScoreDTO> postScore(ScoreDTO scoreDTO, String token) {
        return webClient.post()
                .uri("/score")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .headers(h -> h.setBearerAuth(token))
                .body(Mono.just(scoreDTO), ScoreDTO.class)
                .retrieve()
                .bodyToMono(ScoreDTO.class)
                .doOnSuccess(res -> logger.info("Added score {}", res.getDescription()))
                .onErrorMap(throwable -> exceptionFactory
                        .event("Post score failed with " + scoreDTO.getDescription(), throwable));
    }

    private Mono<String> login() {
        final MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("username", defaultUsername);
        formData.add("password", defaultPassword);
        formData.add("grant_type", "password");

        return webClient.post()
                .uri("/oauth/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .headers(h -> h.setBasicAuth(clientId, clientSecret))
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(DefaultOAuth2AccessToken.class)
                .map(DefaultOAuth2AccessToken::getValue)
                .onErrorMap(throwable -> new EventException("Login failed", throwable))
                .retryWhen(Retry
                        .backoff(3, Duration.ofSeconds(2))
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
                                exceptionFactory.event("Login retries failed", retrySignal.failure()))
                );
    }
}

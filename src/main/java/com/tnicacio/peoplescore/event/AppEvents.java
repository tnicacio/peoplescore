package com.tnicacio.peoplescore.event;

import com.tnicacio.peoplescore.client.PeopleScoreClient;
import com.tnicacio.peoplescore.score.dto.ScoreDTO;
import com.tnicacio.peoplescore.score.enums.Score;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppEvents {

    PeopleScoreClient peopleScoreClient;

    @SneakyThrows
    @EventListener(ApplicationReadyEvent.class)
    public void populateDefaultScores() {
        String token = getToken();
        getDefaultScores().forEach(score -> {
            ScoreDTO scoreDTO = peopleScoreClient.postScore(score, token);
            log.info("Added score {}", scoreDTO);
        });
    }

    private String getToken() {
        OAuth2AccessToken auth2AccessToken = peopleScoreClient.login();
        return auth2AccessToken.getValue();
    }

    private List<ScoreDTO> getDefaultScores() {
        final ScoreDTO insufficient = ScoreDTO.builder()
                .description(Score.INSUFFICIENT.getDescription())
                .initialScore(Score.INSUFFICIENT.initialValue())
                .finalScore(Score.INSUFFICIENT.finalValue())
                .build();
        final ScoreDTO unacceptable = ScoreDTO.builder()
                .description(Score.UNACCEPTABLE.getDescription())
                .initialScore(Score.UNACCEPTABLE.initialValue())
                .finalScore(Score.UNACCEPTABLE.finalValue())
                .build();
        final ScoreDTO acceptable = ScoreDTO.builder()
                .description(Score.ACCEPTABLE.getDescription())
                .initialScore(Score.ACCEPTABLE.initialValue())
                .finalScore(Score.ACCEPTABLE.finalValue())
                .build();
        final ScoreDTO recommendable = ScoreDTO.builder()
                .description(Score.RECOMMENDABLE.getDescription())
                .initialScore(Score.RECOMMENDABLE.initialValue())
                .finalScore(Score.RECOMMENDABLE.finalValue())
                .build();
        return List.of(insufficient, unacceptable, acceptable, recommendable);
    }
}

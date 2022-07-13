package com.tnicacio.peoplescore.client;

import com.tnicacio.peoplescore.score.dto.ScoreDTO;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PeopleScoreClient {

    @Setter(AccessLevel.NONE)
    @Value("${peoplescore.api.url}")
    String peopleScoreApiUrl;

    RestTemplate restTemplate;
    OAuth2RestOperations oAuth2RestOperations;

    public OAuth2AccessToken login() {
        return oAuth2RestOperations.getAccessToken();
    }

    public ScoreDTO postScore(ScoreDTO scoreDTO, String token) {
        String url = peopleScoreApiUrl + "/score";
        HttpEntity<ScoreDTO> entity = new HttpEntity<>(scoreDTO, createHeaders(token));
        return restTemplate.postForObject(url, entity, ScoreDTO.class);
    }

    private HttpHeaders createHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);
        return headers;
    }
}

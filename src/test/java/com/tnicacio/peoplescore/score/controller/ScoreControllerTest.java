package com.tnicacio.peoplescore.score.controller;

import com.tnicacio.peoplescore.score.dto.ScoreDTO;
import com.tnicacio.peoplescore.score.service.ScoreService;
import com.tnicacio.peoplescore.test.util.TestRandomUtils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class ScoreControllerTest {

    @Nested
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class InsertTest {

        @InjectMocks
        ScoreController scoreController;
        @Mock
        ScoreService scoreService;

        @BeforeEach
        void setUp() {
            openMocks(this);
        }

        @Test
        void shouldCallServiceInsertMethodAndReturnCreatedObject() {
            final ScoreDTO inputDTO = TestRandomUtils.randomObject(ScoreDTO.class);
            final ScoreDTO outputDTO = TestRandomUtils.randomObject(ScoreDTO.class);
            final MockHttpServletRequest request = new MockHttpServletRequest();
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

            when(scoreService.insert(inputDTO)).thenReturn(outputDTO);

            final ResponseEntity<ScoreDTO> result = scoreController.insert(inputDTO);

            assertThat(result)
                    .satisfies(res -> {
                        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.CREATED);
                        assertThat(res.getBody()).isEqualTo(outputDTO);
                    })
                    .extracting(HttpEntity::getHeaders)
                    .extracting("Location")
                    .asString()
                    .endsWith(outputDTO.getId().toString());
        }
    }
}
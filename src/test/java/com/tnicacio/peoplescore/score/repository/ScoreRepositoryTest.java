package com.tnicacio.peoplescore.score.repository;

import com.tnicacio.peoplescore.score.enums.Score;
import com.tnicacio.peoplescore.score.model.ScoreModel;
import com.tnicacio.peoplescore.test.builder.TestScoreModelBuilder;
import com.tnicacio.peoplescore.test.util.TestRandomUtils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ScoreRepositoryTest {

    @Nested
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class FindScoreDescriptionByScoreTest {

        @Autowired
        ScoreRepository scoreRepository;

        @BeforeEach
        void setUp() {
            final ScoreModel insufficient = TestScoreModelBuilder.builder()
                    .withDescription(Score.INSUFFICIENT.getDescription())
                    .withInitialScore(Score.INSUFFICIENT.initialValue())
                    .withFinalScore(Score.INSUFFICIENT.finalValue())
                    .build();
            final ScoreModel unacceptable = TestScoreModelBuilder.builder()
                    .withDescription(Score.UNACCEPTABLE.getDescription())
                    .withInitialScore(Score.UNACCEPTABLE.initialValue())
                    .withFinalScore(Score.UNACCEPTABLE.finalValue())
                    .build();
            final ScoreModel acceptable = TestScoreModelBuilder.builder()
                    .withDescription(Score.ACCEPTABLE.getDescription())
                    .withInitialScore(Score.ACCEPTABLE.initialValue())
                    .withFinalScore(Score.ACCEPTABLE.finalValue())
                    .build();
            final ScoreModel recommendable = TestScoreModelBuilder.builder()
                    .withDescription(Score.RECOMMENDABLE.getDescription())
                    .withInitialScore(Score.RECOMMENDABLE.initialValue())
                    .withFinalScore(Score.RECOMMENDABLE.finalValue())
                    .build();
            scoreRepository.saveAll(List.of(insufficient, unacceptable, acceptable, recommendable));
        }

        @AfterEach
        void cleanUp() {
            scoreRepository.deleteAll();
        }

        @Test
        void shouldFindScoreDescriptionFromScoreValue() {
            final Score score = TestRandomUtils.randomEnum(Score.class);

            final Optional<String> result = scoreRepository.findScoreDescriptionByScore(
                    RandomUtils.nextLong(score.initialValue(), score.finalValue()));

            assertThat(result)
                    .isPresent()
                    .get()
                    .isEqualTo(score.getDescription());
        }

        @Test
        void shouldReturnEmptyOptionalWhenScoreOutsideRange() {
            final long scoreInvalid = -1L;

            final Optional<String> result = scoreRepository.findScoreDescriptionByScore(scoreInvalid);

            assertThat(result).isEmpty();
        }

    }

}
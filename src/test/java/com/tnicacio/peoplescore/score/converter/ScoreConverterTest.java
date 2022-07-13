package com.tnicacio.peoplescore.score.converter;

import com.tnicacio.peoplescore.score.dto.ScoreDTO;
import com.tnicacio.peoplescore.score.model.ScoreModel;
import com.tnicacio.peoplescore.test.util.TestRandomUtils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

class ScoreConverterTest {

    @Nested
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class ToModelTest {

        @InjectMocks
        ScoreConverter scoreConverter;

        @BeforeEach
        void setUp() {
            openMocks(this);
        }

        @Test
        void shouldConvertFromDTOToModel() {
            final ScoreDTO scoreDTO = TestRandomUtils.randomObject(ScoreDTO.class);

            final ScoreModel result = scoreConverter.toModel(scoreDTO);

            assertThat(result.getId()).isEqualTo(scoreDTO.getId());
            assertThat(result.getDescription()).isEqualTo(scoreDTO.getDescription());
            assertThat(result.getInitialScore()).isEqualTo(scoreDTO.getInitialScore());
            assertThat(result.getFinalScore()).isEqualTo(scoreDTO.getFinalScore());
        }
    }

    @Nested
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class ToDTOTest {

        @InjectMocks
        ScoreConverter scoreConverter;

        @BeforeEach
        void setUp() {
            openMocks(this);
        }

        @Test
        void shouldConvertFromModelToDTO() {
            final ScoreModel scoreModel = TestRandomUtils.randomObject(ScoreModel.class);

            final ScoreDTO result = scoreConverter.toDTO(scoreModel);

            assertThat(result.getId()).isEqualTo(scoreModel.getId());
            assertThat(result.getDescription()).isEqualTo(scoreModel.getDescription());
            assertThat(result.getInitialScore()).isEqualTo(scoreModel.getInitialScore());
            assertThat(result.getFinalScore()).isEqualTo(scoreModel.getFinalScore());
        }
    }

}
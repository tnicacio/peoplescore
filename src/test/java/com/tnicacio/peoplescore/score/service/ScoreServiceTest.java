package com.tnicacio.peoplescore.score.service;

import com.tnicacio.peoplescore.exception.ResourceNotFoundException;
import com.tnicacio.peoplescore.exception.factory.ExceptionFactory;
import com.tnicacio.peoplescore.score.dto.ScoreDTO;
import com.tnicacio.peoplescore.score.model.ScoreModel;
import com.tnicacio.peoplescore.score.repository.ScoreRepository;
import com.tnicacio.peoplescore.test.util.TestRandomUtils;
import com.tnicacio.peoplescore.util.converter.Converter;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class ScoreServiceTest {

    @Nested
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class InsertTest {

        @InjectMocks
        ScoreService scoreService;

        @Mock
        Converter<ScoreModel, ScoreDTO> scoreConverter;
        @Mock
        ScoreRepository scoreRepository;

        @BeforeEach
        void setUp() {
            openMocks(this);
        }


        @Test
        void shouldSave() {
            final ScoreDTO scoreDTOBeforeSave = TestRandomUtils.randomObject(ScoreDTO.class);
            final ScoreModel scoreModel = TestRandomUtils.randomObject(ScoreModel.class);
            final ScoreDTO scoreDTOAfterSave = TestRandomUtils.randomObject(ScoreDTO.class);

            when(scoreConverter.toModel(scoreDTOBeforeSave)).thenReturn(scoreModel);
            when(scoreConverter.toDTO(scoreModel)).thenReturn(scoreDTOAfterSave);
            when(scoreRepository.save(scoreModel)).thenReturn(scoreModel);

            final ScoreDTO result = scoreService.insert(scoreDTOBeforeSave);

            assertThat(result).isEqualTo(scoreDTOAfterSave);
        }
    }

    @Nested
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class FindScoreDescriptionTest {

        @InjectMocks
        ScoreService scoreService;

        @Mock
        ExceptionFactory exceptionFactory;
        @Mock
        ScoreRepository scoreRepository;

        @BeforeEach
        void setUp() {
            openMocks(this);
        }

        @Test
        void shouldThrowNotFoundWhenScoreDescriptionNotFoundByScoreValue() {
            final long scoreValue = -1L;
            final String exceptionMessage = "Descrição não encontrada para o score: " + scoreValue;
            ResourceNotFoundException resourceNotFoundException = new ResourceNotFoundException(exceptionMessage);

            when(scoreRepository.findScoreDescriptionByScore(scoreValue)).thenReturn(Optional.empty());
            when(exceptionFactory.notFound(exceptionMessage)).thenReturn(resourceNotFoundException);

            assertThatThrownBy(() -> scoreService.findScoreDescription(scoreValue))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage(exceptionMessage);
        }

        @Test
        void shouldFindScoreDescriptionByScoreValue() {
            final long scoreValue = RandomUtils.nextLong(1L, 10L);
            final String scoreDescription = RandomStringUtils.randomAlphabetic(12);

            when(scoreRepository.findScoreDescriptionByScore(scoreValue)).thenReturn(Optional.of(scoreDescription));

            final String result = scoreService.findScoreDescription(scoreValue);

            assertThat(result).isEqualTo(scoreDescription);
        }
    }
}
package com.tnicacio.peoplescore.state.service;

import com.tnicacio.peoplescore.state.repository.StateRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class StateServiceTest {

    @Nested
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class FindStateAbbreviationListByRegionTest {

        @InjectMocks
        StateService stateService;
        @Mock
        StateRepository stateRepository;

        @BeforeEach
        void setUp() {
            openMocks(this);
        }

        @Test
        void shouldCallRepositoryFindStateAbbreviationListByRegion() {
            final String region = RandomStringUtils.randomAlphabetic(1, 10);
            final List<String> stateAbbreviationList = List.of("AA, AB, AC, AD");

            when(stateRepository.findStateAbbreviationListByRegion(region)).thenReturn(stateAbbreviationList);

            final List<String> result = stateService.findStateAbbreviationListByRegion(region);

            assertThat(result).hasSameElementsAs(stateAbbreviationList);
        }
    }

    @Nested
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class StateAbbreviationsValidTest {

        @InjectMocks
        StateService stateService;
        @Mock
        StateRepository stateRepository;

        @BeforeEach
        void setUp() {
            openMocks(this);
        }

        @Test
        void shouldBeValidWhenStateAbbreviationIsNotPersisted() {
            final Set<String> stateAbbreviations = Set.of("AA, AB, AC, AD");

            when(stateRepository.existsByAbbreviationIn(stateAbbreviations)).thenReturn(false);

            final boolean result = stateService.stateAbbreviationsValid(stateAbbreviations);

            assertThat(result).isTrue();
        }

        @Test
        void shouldBeInvalidWhenStateAbbreviationIsAlreadyPersisted() {
            final Set<String> stateAbbreviations = Set.of("AA, AB, AC, AD");

            when(stateRepository.existsByAbbreviationIn(stateAbbreviations)).thenReturn(true);

            final boolean result = stateService.stateAbbreviationsValid(stateAbbreviations);

            assertThat(result).isFalse();
        }
    }

    @Nested
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class ExistsByAbbreviationTest {

        @InjectMocks
        StateService stateService;
        @Mock
        StateRepository stateRepository;

        @BeforeEach
        void setUp() {
            openMocks(this);
        }

        @Test
        void shouldReturnTrueWhenStateAbbreviationIsPersisted() {
            final String stateAbbreviation = RandomStringUtils.randomAlphabetic(2);

            when(stateRepository.existsByAbbreviation(stateAbbreviation)).thenReturn(true);

            final boolean result = stateService.existsByAbbreviation(stateAbbreviation);

            assertThat(result).isTrue();
        }

        @Test
        void shouldReturnFalseWhenStateAbbreviationIsNotPersisted() {
            final String stateAbbreviation = RandomStringUtils.randomAlphabetic(2);

            when(stateRepository.existsByAbbreviation(stateAbbreviation)).thenReturn(false);

            final boolean result = stateService.existsByAbbreviation(stateAbbreviation);

            assertThat(result).isFalse();
        }
    }
}
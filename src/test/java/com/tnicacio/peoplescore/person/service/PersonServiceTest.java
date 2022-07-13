package com.tnicacio.peoplescore.person.service;

import com.tnicacio.peoplescore.exception.ResourceNotFoundException;
import com.tnicacio.peoplescore.exception.factory.ExceptionFactory;
import com.tnicacio.peoplescore.person.dto.PersonDTO;
import com.tnicacio.peoplescore.person.model.PersonModel;
import com.tnicacio.peoplescore.person.repository.PersonRepository;
import com.tnicacio.peoplescore.score.service.ScoreService;
import com.tnicacio.peoplescore.state.service.StateService;
import com.tnicacio.peoplescore.test.builder.TestPersonModelBuilder;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class PersonServiceTest {

    @Nested
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class InsertTest {

        @InjectMocks
        PersonService personService;

        @Mock
        PersonRepository personRepository;
        @Mock
        Converter<PersonModel, PersonDTO> personConverter;

        @BeforeEach
        void setUp() {
            openMocks(this);
        }

        @Test
        void shouldSave() {
            final PersonDTO personDTOBeforeSave = TestRandomUtils.randomObject(PersonDTO.class);
            final PersonModel personModel = TestRandomUtils.randomObject(PersonModel.class);
            final PersonDTO personDTOAfterSave = TestRandomUtils.randomObject(PersonDTO.class);

            when(personConverter.toModel(personDTOBeforeSave)).thenReturn(personModel);
            when(personConverter.toDTO(personModel)).thenReturn(personDTOAfterSave);
            when(personRepository.save(personModel)).thenReturn(personModel);

            final PersonDTO result = personService.insert(personDTOBeforeSave);

            assertThat(result).isEqualTo(personDTOAfterSave);
        }
    }

    @Nested
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class FindByIdTest {

        @InjectMocks
        PersonService personService;

        @Mock
        Converter<PersonModel, PersonDTO> personConverter;
        @Mock
        ExceptionFactory exceptionFactory;
        @Mock
        PersonRepository personRepository;
        @Mock
        ScoreService scoreService;
        @Mock
        StateService stateService;

        @BeforeEach
        void setUp() {
            openMocks(this);
        }

        @Test
        void shouldThrowNotFoundWhenPersonNotFoundById() {
            final Long inputId = RandomUtils.nextLong();
            final String exceptionMessage = "Pessoa não encontrada";
            final ResourceNotFoundException resourceNotFoundException = new ResourceNotFoundException(exceptionMessage);

            when(personRepository.findById(inputId)).thenReturn(Optional.empty());
            when(exceptionFactory.notFound(exceptionMessage)).thenReturn(resourceNotFoundException);

            assertThatThrownBy(() -> personService.findById(inputId))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage(exceptionMessage);
        }

        @Test
        void shouldFindByIdAndAssociateScoreValueToDescriptionAndAssociateStatesWithAffinityRegion() {
            final Long inputId = RandomUtils.nextLong();
            final PersonModel personModelFound = TestPersonModelBuilder.builder().build();
            final PersonDTO personDTO = TestRandomUtils.randomObject(PersonDTO.class);
            final String scoreDescription = RandomStringUtils.randomAlphabetic(12);
            final List<String> stateAbbreviationList = List.of("RJ", "MG", "SP", "ES");

            when(personRepository.findById(inputId)).thenReturn(Optional.of(personModelFound));
            when(personConverter.toDTO(personModelFound)).thenReturn(personDTO);
            when(scoreService.findScoreDescription(personModelFound.getScore())).thenReturn(scoreDescription);
            when(stateService.findStateAbbreviationListByRegion(personModelFound.getRegion())).thenReturn(stateAbbreviationList);

            final PersonDTO result = personService.findById(inputId);

            assertThat(result).extracting(PersonDTO::getScoreDescription).isEqualTo(scoreDescription);
            assertThat(result).extracting(PersonDTO::getAffinityStates).isEqualTo(Set.copyOf(stateAbbreviationList));
        }
    }

    @Nested
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class FindAllTest {

        @InjectMocks
        PersonService personService;

        @Mock
        Converter<PersonModel, PersonDTO> personConverter;
        @Mock
        ExceptionFactory exceptionFactory;
        @Mock
        PersonRepository personRepository;
        @Mock
        ScoreService scoreService;
        @Mock
        StateService stateService;

        @BeforeEach
        void setUp() {
            openMocks(this);
        }

        @Test
        void shouldThrowNotFoundWhenPersonNotFoundById() {
            final String exceptionMessage = "Nenhuma pessoa foi encontrada";
            final ResourceNotFoundException resourceNotFoundException = new ResourceNotFoundException(exceptionMessage);

            when(personRepository.findPeopleBasicData()).thenReturn(Collections.emptyList());
            when(exceptionFactory.notFound(exceptionMessage)).thenReturn(resourceNotFoundException);

            assertThatThrownBy(() -> personService.findAll())
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessage(exceptionMessage);
        }

        @Test
        void shouldFindPeopleBasicDataAndAssociateStatesWithAffinityRegion() {
            final List<PersonDTO> peopleBasicDataList = TestRandomUtils.randomList(PersonDTO.class, 10);
            final List<String> stateAbbreviationList = List.of("RJ", "MG", "SP", "ES");

            when(personRepository.findPeopleBasicData()).thenReturn(peopleBasicDataList);
            when(stateService.findStateAbbreviationListByRegion(anyString())).thenReturn(stateAbbreviationList);

            final List<PersonDTO> result = personService.findAll();

            assertThat(result)
                    .isNotEmpty()
                    .extracting(PersonDTO::getAffinityStates)
                    .allSatisfy(states -> assertThat(states).isEqualTo(Set.copyOf(stateAbbreviationList)));
        }
    }
}
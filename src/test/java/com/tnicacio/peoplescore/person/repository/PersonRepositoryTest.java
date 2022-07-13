package com.tnicacio.peoplescore.person.repository;

import com.tnicacio.peoplescore.person.dto.PersonDTO;
import com.tnicacio.peoplescore.person.model.PersonModel;
import com.tnicacio.peoplescore.score.model.ScoreModel;
import com.tnicacio.peoplescore.score.repository.ScoreRepository;
import com.tnicacio.peoplescore.test.builder.TestPersonModelBuilder;
import com.tnicacio.peoplescore.test.builder.TestScoreModelBuilder;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PersonRepositoryTest {

    @Nested
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class FindPeopleBasicDataTest {

        @Autowired
        private PersonRepository personRepository;
        @Autowired
        private ScoreRepository scoreRepository;

        @BeforeEach
        void setUp() {
            final ScoreModel insufficient = TestScoreModelBuilder.builder().withDescription("Insuficiente").withInitialScore(0L).withFinalScore(200L).build();
            final ScoreModel unacceptable = TestScoreModelBuilder.builder().withDescription("Inaceitável").withInitialScore(201L).withFinalScore(500L).build();
            final ScoreModel acceptable = TestScoreModelBuilder.builder().withDescription("Aceitável").withInitialScore(501L).withFinalScore(700L).build();
            final ScoreModel recommendable = TestScoreModelBuilder.builder().withDescription("Recomendável").withInitialScore(701L).withFinalScore(1000L).build();
            scoreRepository.saveAll(List.of(insufficient, unacceptable, acceptable, recommendable));
        }

        @AfterEach
        void cleanUp() {
            personRepository.deleteAll();
            scoreRepository.deleteAll();
        }

        @Test
        void shouldFindPeopleBasicData() {
            final PersonModel joao = TestPersonModelBuilder.builder()
                    .withName("João")
                    .withScore(610L)
                    .withPersonModelPersistenceFunction(personRepository::save)
                    .build();
            final PersonModel pedro = TestPersonModelBuilder.builder()
                    .withName("Pedro")
                    .withScore(950L)
                    .withPersonModelPersistenceFunction(personRepository::save)
                    .build();

            final List<PersonDTO> result = personRepository.findPeopleBasicData();

            assertThat(result).isNotEmpty();
            assertThat(result).extracting(PersonDTO::getId).containsExactly(joao.getId(), pedro.getId());
            assertThat(result).extracting(PersonDTO::getName).containsExactly(joao.getName(), pedro.getName());
            assertThat(result).extracting(PersonDTO::getCity).containsExactly(joao.getCity(), pedro.getCity());
            assertThat(result).extracting(PersonDTO::getState).containsExactly(joao.getState(), pedro.getState());
            assertThat(result).extracting(PersonDTO::getRegion).containsExactly(joao.getRegion(), pedro.getRegion());
            assertThat(result).extracting(PersonDTO::getScoreDescription).containsExactly("Aceitável", "Recomendável");
        }

        @Test
        void shouldFindPeopleBasicDataOrderedByNameAsc() {
            final PersonModel pedro = TestPersonModelBuilder.builder()
                    .withName("Pedro")
                    .withScore(950L)
                    .withPersonModelPersistenceFunction(personRepository::save)
                    .build();
            final PersonModel joao = TestPersonModelBuilder.builder()
                    .withName("João")
                    .withScore(610L)
                    .withPersonModelPersistenceFunction(personRepository::save)
                    .build();

            final List<PersonDTO> result = personRepository.findPeopleBasicData();

            assertThat(result)
                    .isNotEmpty()
                    .hasSize(2)
                    .extracting(PersonDTO::getId)
                    .elements(0, 1)
                    .containsExactly(joao.getId(), pedro.getId());
        }
    }

}
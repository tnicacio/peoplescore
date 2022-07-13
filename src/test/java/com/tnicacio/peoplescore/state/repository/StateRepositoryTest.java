package com.tnicacio.peoplescore.state.repository;

import com.tnicacio.peoplescore.affinity.model.AffinityModel;
import com.tnicacio.peoplescore.affinity.repository.AffinityRepository;
import com.tnicacio.peoplescore.state.model.StateModel;
import com.tnicacio.peoplescore.test.builder.TestAffinityModelBuilder;
import com.tnicacio.peoplescore.test.builder.TestStateModelBuilder;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StateRepositoryTest {

    @Nested
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class FindStateAbbreviationListByRegionTest {

        @Autowired
        StateRepository stateRepository;
        @Autowired
        AffinityRepository affinityRepository;

        @BeforeEach
        void setUp() {
            final String region = "regiao1";
            final AffinityModel affinity = TestAffinityModelBuilder.builder()
                    .withRegion(region)
                    .withAffinityPersistenceFunction(affinityRepository::save)
                    .build();
            TestStateModelBuilder.listBuilder()
                    .withAffinity(affinity)
                    .withStateModelPersistenceFunction(stateRepository::save)
                    .buildList();
        }

        @AfterEach
        void cleanUp() {
            stateRepository.deleteAll();
        }

        @Test
        void shouldFindStateAbbreviationListByRegion() {
            final String region = "regiao2";
            final AffinityModel affinity = TestAffinityModelBuilder.builder()
                    .withRegion(region)
                    .withAffinityPersistenceFunction(affinityRepository::save)
                    .build();
            final List<String> states = TestStateModelBuilder.listBuilder()
                    .withAffinity(affinity)
                    .withStateModelPersistenceFunction(stateRepository::save)
                    .buildList()
                    .stream()
                    .map(StateModel::getAbbreviation)
                    .collect(Collectors.toList());

            final List<String> result = stateRepository.findStateAbbreviationListByRegion(region);

            assertThat(result).isNotEmpty().hasSameElementsAs(states);
        }
    }
}
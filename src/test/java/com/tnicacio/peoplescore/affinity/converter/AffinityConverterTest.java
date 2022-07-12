package com.tnicacio.peoplescore.affinity.converter;

import com.tnicacio.peoplescore.affinity.dto.AffinityDTO;
import com.tnicacio.peoplescore.affinity.model.AffinityModel;
import com.tnicacio.peoplescore.state.model.StateModel;
import com.tnicacio.peoplescore.test.util.TestRandomUtils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

class AffinityConverterTest {

    @Nested
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class ToModelTest {

        @InjectMocks
        AffinityConverter affinityConverter;

        @BeforeEach
        void setUp() {
            openMocks(this);
        }

        @Test
        void shouldConvertFromDTOToModel() {
            final AffinityDTO affinityDTO = TestRandomUtils.randomObject(AffinityDTO.class);

            final AffinityModel result = affinityConverter.toModel(affinityDTO);

            assertThat(result.getId()).isEqualTo(affinityDTO.getId());
            assertThat(result.getRegion()).isEqualTo(affinityDTO.getRegion());
            assertThat(result.getStates().stream().map(StateModel::getAbbreviation)).containsExactlyInAnyOrderElementsOf(affinityDTO.getStates());
        }
    }

    @Nested
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class ToDTOTest {

        @InjectMocks
        AffinityConverter affinityConverter;

        @BeforeEach
        void setUp() {
            openMocks(this);
        }

        @Test
        void shouldConvertFromModelToDTO() {
            final AffinityModel affinityModel = TestRandomUtils.randomObject(AffinityModel.class);
            final Set<String> affinityModelStateAbbreviationList =
                    affinityModel.getStates().stream().map(StateModel::getAbbreviation).collect(Collectors.toSet());

            final AffinityDTO result = affinityConverter.toDTO(affinityModel);

            assertThat(result.getId()).isEqualTo(affinityModel.getId());
            assertThat(result.getRegion()).isEqualTo(affinityModel.getRegion());
            assertThat(result.getStates()).containsExactlyInAnyOrderElementsOf(affinityModelStateAbbreviationList);
        }
    }

}
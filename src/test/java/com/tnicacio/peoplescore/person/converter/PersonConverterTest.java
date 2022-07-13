package com.tnicacio.peoplescore.person.converter;

import com.tnicacio.peoplescore.person.dto.PersonDTO;
import com.tnicacio.peoplescore.person.model.PersonModel;
import com.tnicacio.peoplescore.test.util.TestRandomUtils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

class PersonConverterTest {

    @Nested
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class ToModelTest {

        @InjectMocks
        PersonConverter personConverter;

        @BeforeEach
        void setUp() {
            openMocks(this);
        }

        @Test
        void shouldConvertFromDTOToModel() {
            final PersonDTO personDTO = TestRandomUtils.randomObject(PersonDTO.class);

            final PersonModel result = personConverter.toModel(personDTO);

            assertThat(result.getId()).isEqualTo(personDTO.getId());
            assertThat(result.getName()).isEqualTo(personDTO.getName());
            assertThat(result.getPhone()).isEqualTo(personDTO.getPhone());
            assertThat(result.getAge()).isEqualTo(personDTO.getAge());
            assertThat(result.getCity()).isEqualTo(personDTO.getCity());
            assertThat(result.getState()).isEqualTo(personDTO.getState());
            assertThat(result.getScore()).isEqualTo(personDTO.getScore());
            assertThat(result.getRegion()).isEqualTo(personDTO.getRegion());
        }
    }

    @Nested
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class ToDTOTest {

        @InjectMocks
        PersonConverter personConverter;

        @BeforeEach
        void setUp() {
            openMocks(this);
        }

        @Test
        void shouldConvertFromModelToDTO() {
            final PersonModel personModel = TestRandomUtils.randomObject(PersonModel.class);

            final PersonDTO result = personConverter.toDTO(personModel);

            assertThat(result.getId()).isEqualTo(personModel.getId());
            assertThat(result.getName()).isEqualTo(personModel.getName());
            assertThat(result.getPhone()).isEqualTo(personModel.getPhone());
            assertThat(result.getAge()).isEqualTo(personModel.getAge());
            assertThat(result.getCity()).isEqualTo(personModel.getCity());
            assertThat(result.getState()).isEqualTo(personModel.getState());
            assertThat(result.getScore()).isEqualTo(personModel.getScore());
            assertThat(result.getRegion()).isEqualTo(personModel.getRegion());
        }
    }
}
package com.tnicacio.peoplescore.person.controller;

import com.tnicacio.peoplescore.person.dto.PersonDTO;
import com.tnicacio.peoplescore.person.service.PersonService;
import com.tnicacio.peoplescore.test.util.TestRandomUtils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.RandomUtils;
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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class PersonControllerTest {

    @Nested
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class InsertTest {

        @InjectMocks
        PersonController personController;
        @Mock
        PersonService personService;

        @BeforeEach
        void setUp() {
            openMocks(this);
        }

        @Test
        void shouldCallServiceInsertMethodAndReturnCreatedObject() {
            final PersonDTO inputDTO = TestRandomUtils.randomObject(PersonDTO.class);
            final PersonDTO outputDTO = TestRandomUtils.randomObject(PersonDTO.class);
            final MockHttpServletRequest request = new MockHttpServletRequest();
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

            when(personService.insert(inputDTO)).thenReturn(outputDTO);

            final ResponseEntity<PersonDTO> result = personController.insert(inputDTO);

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

    @Nested
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class FindByIdTest {

        @InjectMocks
        PersonController personController;
        @Mock
        PersonService personService;

        @BeforeEach
        void setUp() {
            openMocks(this);
        }

        @Test
        void shouldCallServiceFindByIdMethodAndReturnFoundObject() {
            final Long inputId = RandomUtils.nextLong();
            final PersonDTO outputDTO = TestRandomUtils.randomObject(PersonDTO.class);

            when(personService.findById(inputId)).thenReturn(outputDTO);

            final ResponseEntity<PersonDTO> result = personController.findById(inputId);

            assertThat(result).satisfies(res -> {
                assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(res.getBody()).isEqualTo(outputDTO);
            });
        }
    }

    @Nested
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class FindAllTest {

        @InjectMocks
        PersonController personController;
        @Mock
        PersonService personService;

        @BeforeEach
        void setUp() {
            openMocks(this);
        }

        @Test
        void shouldCallServiceFindAllMethodAndReturnListOfFoundObjects() {
            final List<PersonDTO> outputDTOList = TestRandomUtils.randomList(PersonDTO.class, RandomUtils.nextInt(1,
                    10));
            final List<Long> expectedIdList = outputDTOList.stream().map(PersonDTO::getId).collect(Collectors.toList());

            when(personService.findAll()).thenReturn(outputDTOList);

            final ResponseEntity<List<PersonDTO>> result = personController.findAll();

            assertThat(result).extracting(HttpEntity::getBody).isNotNull().asList().isNotEmpty();
            assertThat(Objects.requireNonNull(result.getBody()).stream()
                    .map(PersonDTO::getId)
                    .collect(Collectors.toList()))
                    .hasSameElementsAs(expectedIdList);
        }
    }
}
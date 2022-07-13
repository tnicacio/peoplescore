package com.tnicacio.peoplescore.affinity.controller;

import com.tnicacio.peoplescore.affinity.dto.AffinityDTO;
import com.tnicacio.peoplescore.affinity.service.AffinityService;
import com.tnicacio.peoplescore.test.util.TestRandomUtils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;


class AffinityControllerTest {

    @Nested
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class InsertTest {

        @InjectMocks
        AffinityController affinityController;
        @Mock
        AffinityService affinityService;

        @BeforeEach
        void setUp() {
            openMocks(this);
        }

        @Test
        void shouldCallServiceInsertMethodAndReturnCreatedObject() {
            final AffinityDTO inputDTO = TestRandomUtils.randomObject(AffinityDTO.class);
            final AffinityDTO outputDTO = TestRandomUtils.randomObject(AffinityDTO.class);
            final MockHttpServletRequest request = new MockHttpServletRequest();
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

            when(affinityService.insert(inputDTO)).thenReturn(outputDTO);

            final ResponseEntity<AffinityDTO> result = affinityController.insert(inputDTO);

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

}
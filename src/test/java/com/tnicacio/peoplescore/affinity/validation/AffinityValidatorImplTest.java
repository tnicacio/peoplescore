package com.tnicacio.peoplescore.affinity.validation;

import com.tnicacio.peoplescore.affinity.dto.AffinityDTO;
import com.tnicacio.peoplescore.affinity.service.AffinityService;
import com.tnicacio.peoplescore.state.service.StateService;
import com.tnicacio.peoplescore.test.builder.TestAffinityDTOBuilder;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.validation.ClockProvider;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class AffinityValidatorImplTest {

    @Nested
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class IsValidTest {

        @InjectMocks
        AffinityValidatorImpl affinityValidator;

        @Mock
        AffinityService affinityService;
        @Mock
        StateService stateService;

        @Captor
        ArgumentCaptor<String> stateAbbreviationCaptor;

        ConstraintValidatorContext context;

        @BeforeEach
        void setUp() {
            openMocks(this);
            context = new DummyConstraintValidatorContextImpl();
        }

        @Test
        void shouldValidateRegionAndStates() {
            final String state1 = "AA";
            final String state2 = "AB";
            final String state3 = "AC";
            final AffinityDTO affinityDTO = TestAffinityDTOBuilder.builder()
                    .withStates(Set.of(state1, state2, state3))
                    .build();

            when(affinityService.regionValid(affinityDTO.getRegion())).thenReturn(false);
            when(stateService.stateAbbreviationsValid(affinityDTO.getStates())).thenReturn(false);
            when(stateService.existsByAbbreviation(state1)).thenReturn(false);
            when(stateService.existsByAbbreviation(state2)).thenReturn(true);
            when(stateService.existsByAbbreviation(state3)).thenReturn(true);

            final boolean result = affinityValidator.isValid(affinityDTO, context);

            verify(affinityService, only()).regionValid(affinityDTO.getRegion());
            verify(stateService, times(1)).stateAbbreviationsValid(affinityDTO.getStates());
            verify(stateService, times(3)).existsByAbbreviation(stateAbbreviationCaptor.capture());

            assertThat(result).isFalse();
            assertThat(stateAbbreviationCaptor.getAllValues())
                    .asList()
                    .isNotEmpty()
                    .containsExactlyInAnyOrder(state1, state2, state3);
        }

        private class DummyConstraintValidatorContextImpl implements ConstraintValidatorContext {

            @Override
            public void disableDefaultConstraintViolation() {
            }

            @Override
            public String getDefaultConstraintMessageTemplate() {
                return null;
            }

            @Override
            public ClockProvider getClockProvider() {
                return null;
            }

            @Override
            public ConstraintViolationBuilder buildConstraintViolationWithTemplate(String messageTemplate) {
                return new ConstraintViolationBuilder() {
                    @Override
                    public NodeBuilderDefinedContext addNode(String name) {
                        return null;
                    }

                    @Override
                    public NodeBuilderCustomizableContext addPropertyNode(String name) {
                        return new NodeBuilderCustomizableContext() {
                            @Override
                            public NodeContextBuilder inIterable() {
                                return null;
                            }

                            @Override
                            public NodeBuilderCustomizableContext inContainer(Class<?> containerClass,
                                                                              Integer typeArgumentIndex) {
                                return null;
                            }

                            @Override
                            public NodeBuilderCustomizableContext addNode(String name) {
                                return null;
                            }

                            @Override
                            public NodeBuilderCustomizableContext addPropertyNode(String name) {
                                return null;
                            }

                            @Override
                            public LeafNodeBuilderCustomizableContext addBeanNode() {
                                return null;
                            }

                            @Override
                            public ContainerElementNodeBuilderCustomizableContext addContainerElementNode(String name,
                                                                                                          Class<?> containerType, Integer typeArgumentIndex) {
                                return null;
                            }

                            @Override
                            public ConstraintValidatorContext addConstraintViolation() {
                                return null;
                            }
                        };
                    }

                    @Override
                    public LeafNodeBuilderCustomizableContext addBeanNode() {
                        return null;
                    }

                    @Override
                    public ContainerElementNodeBuilderCustomizableContext addContainerElementNode(String name,
                                                                                                  Class<?> containerType,
                                                                                                  Integer typeArgumentIndex) {
                        return null;
                    }

                    @Override
                    public NodeBuilderDefinedContext addParameterNode(int index) {
                        return null;
                    }

                    @Override
                    public ConstraintValidatorContext addConstraintViolation() {
                        return null;
                    }
                };
            }

            @Override
            public <T> T unwrap(Class<T> type) {
                return null;
            }
        }
    }

}
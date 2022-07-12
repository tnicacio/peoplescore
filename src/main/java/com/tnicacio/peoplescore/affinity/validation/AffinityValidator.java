package com.tnicacio.peoplescore.affinity.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = AffinityValidatorImpl.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AffinityValidator {
    String message() default "Região já cadastrada";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
package com.invoice.web.infrastructure.utils.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NoNullStringValidator.class)
public @interface NoNullString {
    String message() default "Field can not contain only null string or spaces";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

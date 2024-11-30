package com.invoice.web.infrastructure.utils.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoNullStringValidator implements ConstraintValidator<NoNullString, String> {

    @Override
    public void initialize(NoNullString noNullString) {
        /* This method Initializes the validator in preparation
        for isValid(Object, ConstraintValidatorContext) calls.

        There is no use of this method for us.
        */
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || !value.matches("^(\\s*null\\s*|\\s*NULL\\s*)$");
    }
}

package com.invoice.web.infrastructure.utils.validation;

import com.invoice.web.api.dto.request.CreateInvoiceRequest;
import com.invoice.web.api.dto.request.CreateUserRequest;
import com.invoice.web.api.dto.request.LoginRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.Set;

public class RequestParameterValidator {

    private RequestParameterValidator() {
    }

    public static void loginRequest(LoginRequest request)
            throws ConstraintViolationException {

        if (request == null)
            throw new ConstraintViolationException("Login Request should not be null", null);
        validateRequest(request);
    }


    public static void createUserRequest(CreateUserRequest request)
            throws ConstraintViolationException {

        if (request == null)
            throw new ConstraintViolationException("Create User Request should not be null", null);
        validateRequest(request);
    }



    public static void createInvoiceValidation(CreateInvoiceRequest request) throws ConstraintViolationException {

        if (request == null)
            throw new ConstraintViolationException("Create Invoice Validation request should not be null", null);
        validateRequest(request);
    }

    public static <T> void commonValidateRequest(T request) throws ConstraintViolationException {

        if (request == null)
            throw new ConstraintViolationException("Create Invoice Validation request should not be null", null);
        validateRequest(request);
    }

    private static <T> void validateRequest(T request) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(request);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
    }


}

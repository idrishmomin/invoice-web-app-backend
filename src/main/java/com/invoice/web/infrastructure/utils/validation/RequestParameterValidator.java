package com.invoice.web.infrastructure.utils.validation;

import com.invoice.web.api.dto.request.CreateInvoiceRequest;
import com.invoice.web.api.dto.request.LoginRequest;
import com.invoice.web.infrastructure.exception.CommonException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.Set;

public class RequestParameterValidator {


    private RequestParameterValidator() {
    }

    public static void validateBankTransferRequest(CreateInvoiceRequest request)
            throws ConstraintViolationException, CommonException {

        if (request == null)
            throw new ConstraintViolationException("Money Transfer Request should not be null", null);
        validateRequest(request);
    }


    public static void accountNameValidateRequest(LoginRequest request) throws ConstraintViolationException {

        if (request == null)
            throw new ConstraintViolationException("AccountName Validation request should not be null", null);

        validateRequest(request);
    }


    private static <T> void validateRequest(T request) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(request);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
    }

    public static void validateAmount(String amount) throws CommonException {
        if (!amount.matches("^0*[1-9]\\d*$"))
            throw new CommonException("Amount should be greater than zero, no decimal value allowed", null);
    }

    public static void validateCurrency(String currency) throws CommonException {
        if (!currency.matches("^[A-Z]{3}$"))
            throw new CommonException("Invalid currency code. Please enter 3-letter ISO currency code", null);
    }

}

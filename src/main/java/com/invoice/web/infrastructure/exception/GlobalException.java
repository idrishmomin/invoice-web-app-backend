package com.invoice.web.infrastructure.exception;

import com.invoice.web.api.dto.response.Response;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serial;

@ControllerAdvice
@AllArgsConstructor
@Log4j2
public class GlobalException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    @ExceptionHandler(DaoException.class)
    public @ResponseBody ResponseEntity<Object> daoException(DaoException exception) {
        log.error("Error with database operation: {}", exception.getMessage());
        log.error("Application failure occurred by DaoException!", exception);


        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public @ResponseBody ResponseEntity<Object> exception(Exception exception) {
        log.error("Internal Server Error: {}", exception.getMessage());
        log.error("Application failure occurred by unknown Exception!", exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public @ResponseBody ResponseEntity<Object> constraintViolationException(ConstraintViolationException exception) {
        log.error("Constraint violation Error: {}", exception.getMessage(), exception);
        log.error("Application failure occurred by ConstraintViolationException !", exception);

        String error = exception.getConstraintViolations() != null ?
                exception.getConstraintViolations().stream()
                        .map(ConstraintViolation::getMessage)
                        .findFirst().orElse("Validation Error")
                : exception.getMessage();

        log.info("Application failure occurred by ConstraintViolationException : {}", error);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


}

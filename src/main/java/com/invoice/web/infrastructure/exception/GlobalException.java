package com.invoice.web.infrastructure.exception;

import com.invoice.web.api.dto.response.Response;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
    public @ResponseBody ResponseEntity<Response<Object>> daoException(DaoException exception) {
        log.error("Error with database operation: {}", exception.getMessage());
        log.error("Application failure occurred by DaoException!", exception);

        Response<Object> response = Response.<Object>builder()
                .response(exception.getMessage())
                .build();

        return ResponseEntity.ok(response);
    }


    @ExceptionHandler(Exception.class)
    public @ResponseBody ResponseEntity<Response<Object>> exception(Exception exception) {
        log.error("Internal Server Error: {}", exception.getMessage());
        log.error("Application failure occurred by unknown Exception!", exception);

        Response<Object> response = Response.<Object>builder()
                .response(exception.getMessage())
                .build();

        return ResponseEntity.ok(response);
    }


    @ExceptionHandler(CommonException.class)
    public @ResponseBody ResponseEntity<Response<Object>> commonException(CommonException exception) {
        log.error("Constraint violation Error: {}", exception.getMessage(), exception);
        log.error("Application failure occurred by CommonException !", exception);

        Response<Object> response = Response.<Object>builder()
                .response(exception.getMessage())
                .build();

        return ResponseEntity.ok(response);
    }
    
}

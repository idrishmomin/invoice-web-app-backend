package com.invoice.web.infrastructure.exception;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonException extends Exception {

    private String message;
    private String statusCode;

    public CommonException(String message, String statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public CommonException(String message) {
        super(message);
        this.statusCode = null;
    }
}

package com.invoice.web.infrastructure.exception;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public class DataParseException extends RuntimeException {
    public DataParseException(String message, Throwable cause) {
        super(message, cause);
    }

}

package com.example.projectemailmarketingbe.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadRequestException extends RuntimeException {
    private final HttpStatus STATUS;

    public BadRequestException(String message) {
        super(message);
        STATUS = HttpStatus.BAD_REQUEST;
    }

}

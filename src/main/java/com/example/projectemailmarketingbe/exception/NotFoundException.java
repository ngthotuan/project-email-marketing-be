package com.example.projectemailmarketingbe.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends RuntimeException{
    public final HttpStatus STATUS;

    public NotFoundException(String message) {
        super(message);
        STATUS = HttpStatus.NOT_FOUND;
    }
}

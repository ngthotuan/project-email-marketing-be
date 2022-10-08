package com.example.projectemailmarketingbe.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExpiredException extends RuntimeException{
    private static HttpStatus STATUS;

    public ExpiredException(String message) {
        super(message);
        STATUS = HttpStatus.EXPECTATION_FAILED;
    }
}

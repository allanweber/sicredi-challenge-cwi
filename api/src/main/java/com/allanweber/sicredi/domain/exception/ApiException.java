package com.allanweber.sicredi.domain.exception;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
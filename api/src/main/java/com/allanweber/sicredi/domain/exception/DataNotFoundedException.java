package com.allanweber.sicredi.domain.exception;

public class DataNotFoundedException extends RuntimeException {
    public DataNotFoundedException(String message) {
        super(message);
    }
}

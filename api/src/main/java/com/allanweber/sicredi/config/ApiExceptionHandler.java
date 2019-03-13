package com.allanweber.sicredi.config;

import com.allanweber.sicredi.domain.exception.ApiException;
import com.allanweber.sicredi.domain.exception.DataNotFoundedException;
import com.allanweber.sicredi.domain.exception.ExceptionResponse;
import com.allanweber.sicredi.domain.exception.UserAreadyVotedException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiException.class})
    protected ResponseEntity<ExceptionResponse> handleApiException(ApiException ex) {

        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = {DataNotFoundedException.class})
    protected ResponseEntity<ExceptionResponse> handleDataNotFounded(DataNotFoundedException ex) {

        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage()), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<ExceptionResponse> handleConstraintViolationException(ConstraintViolationException ex) {

        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<ExceptionResponse> handleException(IllegalArgumentException ex) {

        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UserAreadyVotedException.class})
    protected ResponseEntity<ExceptionResponse> handleUserAreadyVotedException(UserAreadyVotedException ex) {

        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ConversionFailedException.class})
    protected ResponseEntity<ExceptionResponse> handleConversionFailedException(ConversionFailedException ex) {

        return new ResponseEntity<>(new ExceptionResponse(
                String.format("Cannot convert %s to %s. Exception: %s", ex.getValue(), ex.getTargetType(), ex.getMessage())
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    protected ResponseEntity<ExceptionResponse> handleConversionFailedException(MethodArgumentTypeMismatchException ex) {

        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage()
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ExceptionResponse> handleException(Exception ex) {

        return new ResponseEntity<>(
                new ExceptionResponse(String.format("Not well treated exception: %s - Exception Type: %s"
                        , ex.getMessage(),
                        ex.getClass()))
                , HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
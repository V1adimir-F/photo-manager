package com.github.photomanager.advice;

import com.github.photomanager.dto.response.ErrorDetailsResponse;
import com.github.photomanager.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * The ExceptionControllerAdvice class is used to handle and manage exceptions in a Java application.
 *
 * @author Vladimir F.
 */
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ErrorDetailsResponse> badRequestExceptionHandler(BadRequestException exception) {
        ErrorDetailsResponse errorDetailsResponse = new ErrorDetailsResponse(exception.getMessage());
        return new ResponseEntity<>(errorDetailsResponse, HttpStatus.BAD_REQUEST);
    }
}

package com.github.photomanager.exception;

/**
 * The class `BadRequestException` is a custom exception that extends the `RuntimeException` class and is used to represent
 * a bad request error.
 *
 * @author Vladimir F.
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}

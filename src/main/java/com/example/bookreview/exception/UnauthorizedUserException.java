package com.example.bookreview.exception;

public class UnauthorizedUserException extends Throwable {
    public UnauthorizedUserException(String message) {
        super(message);
    }
}

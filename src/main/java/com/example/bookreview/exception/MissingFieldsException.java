package com.example.bookreview.exception;

public class MissingFieldsException extends RuntimeException {

    public MissingFieldsException(String message) {
        super(message);
    }
}

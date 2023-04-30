package com.example.bookreview.exception;

public class BookNotFoundException extends Throwable {
    public BookNotFoundException(String message) {

        super(message);
    }
}

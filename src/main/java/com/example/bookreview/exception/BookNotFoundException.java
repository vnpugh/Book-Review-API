package com.example.bookreview.exception;

/**
 * created to handle the exception when the book is not found.
 */
public class BookNotFoundException extends Throwable {
    public BookNotFoundException(String message) {

        super(message);
    }
}

package com.example.bookreview.exception;

public class MissingFieldsException extends RuntimeException {

    /**
     * created this exception to handle the search parameters when the user creates a review,
       or searches for a book or review.
     * @param message
     */
    public MissingFieldsException(String message) {
        super(message);
    }
}

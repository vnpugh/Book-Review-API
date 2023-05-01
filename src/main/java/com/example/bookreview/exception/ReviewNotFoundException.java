package com.example.bookreview.exception;

/**
 * created exception to handle when a review is not found.
 */
public class ReviewNotFoundException extends Throwable {
    public ReviewNotFoundException(String message) {
        super(message);



    }
}

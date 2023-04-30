package com.example.bookreview.exception;


//custom exception used to throw an exception if there is an error saving the review.
public class FailedToSaveReviewException extends Throwable {
    public FailedToSaveReviewException(String message) {

        super(message);
    }
}

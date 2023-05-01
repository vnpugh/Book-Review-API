package com.example.bookreview.exception;

/**
 * created this exception to handle the case where a user is not logged in, and wants
  to create a review.
 */
public class UserNotLoggedInException extends Throwable {
    public UserNotLoggedInException(String message) {
        super(message);

    }


}

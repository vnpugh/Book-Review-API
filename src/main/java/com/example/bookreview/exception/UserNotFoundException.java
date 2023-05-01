package com.example.bookreview.exception;


/**
 * created this exception to handle the case where a user is not logged in and tries to access private info.
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}

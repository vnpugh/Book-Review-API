package com.example.bookreview.exception;

/**
 * created this exception to handle unauthorized users.
 * This exception will be thrown when a user tries to access a resource that they are not authorized to access.
 * For example, a user must be logged-in to delete a review. If not, the exception is thrown.
 */
public class UnauthorizedUserException extends Throwable {
    public UnauthorizedUserException(String message) {
        super(message);
    }
}

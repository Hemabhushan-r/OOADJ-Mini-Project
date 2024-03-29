package com.stockportfolio.stockservice.Exceptions;

public class UserPasswordMismatchException extends Exception {
    public UserPasswordMismatchException() {
        super("Password mismatch. Please try again.");
    }

    public UserPasswordMismatchException(String message) {
        super(message);
    }
}

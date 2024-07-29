package com.example.demo.exception;

public class NonExistingBookException extends RuntimeException{

    private static final String ERROR_MESSAGE = "Book does not exist";

    public static NonExistingBookException of() {
        return new NonExistingBookException();
    }

    protected NonExistingBookException() {
        super(ERROR_MESSAGE);
    }
}

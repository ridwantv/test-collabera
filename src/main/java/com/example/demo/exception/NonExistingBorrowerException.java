package com.example.demo.exception;

public class NonExistingBorrowerException extends RuntimeException{

    private static final String ERROR_MESSAGE = "Borrower does not exist";

    public static NonExistingBorrowerException of() {
        return new NonExistingBorrowerException();
    }

    protected NonExistingBorrowerException() {
        super(ERROR_MESSAGE);
    }
}

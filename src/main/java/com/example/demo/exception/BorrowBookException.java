package com.example.demo.exception;

public class BorrowBookException extends RuntimeException{

    private static final String ERROR_MESSAGE = "Book is not available to borrow";

    public static BorrowBookException of() {
        return new BorrowBookException();
    }

    protected BorrowBookException() {
        super(ERROR_MESSAGE);
    }
}

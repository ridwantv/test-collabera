package com.example.demo.exception;

public class AddBookException extends RuntimeException{

    private static final String ERROR_MESSAGE = "Book's title and author do not match with existing book with same ISBN";

    public static AddBookException of() {
        return new AddBookException();
    }

    protected AddBookException() {
        super(ERROR_MESSAGE);
    }
}

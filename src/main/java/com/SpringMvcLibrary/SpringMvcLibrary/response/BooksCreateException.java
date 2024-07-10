package com.SpringMvcLibrary.SpringMvcLibrary.response;

public class BooksCreateException extends RuntimeException{
    public BooksCreateException(String message) {
        super(message);
    }

    public BooksCreateException() {

    }
}

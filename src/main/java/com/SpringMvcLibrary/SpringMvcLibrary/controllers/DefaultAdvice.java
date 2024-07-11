package com.SpringMvcLibrary.SpringMvcLibrary.controllers;

import com.SpringMvcLibrary.SpringMvcLibrary.response.*;
import com.SpringMvcLibrary.SpringMvcLibrary.response.Exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice()
public class DefaultAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {

        return new ResponseEntity<>(ErrorResponse.create(e, HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка на сервере"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    private ResponseEntity<BooksErrorResponse> handlerException(BooksNotFoundExcention e) {
        BooksErrorResponse response = new BooksErrorResponse("Заказ с таким id не найдем", System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<BooksErrorResponse> handlerException(BooksCreateException e) {
        BooksErrorResponse response = new BooksErrorResponse(e.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<AuthorsErrorResponse> handlerException(AuthorsNotFoundException e) {
        AuthorsErrorResponse response = new AuthorsErrorResponse("Заказ с таким id не найдем", System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<AuthorsErrorResponse> handlerException(AuthorsCreateException e) {
        AuthorsErrorResponse response = new AuthorsErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
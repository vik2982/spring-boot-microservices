package com.va.books.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

  @ExceptionHandler(BookException.class)
  public ResponseEntity<ErrorResponse> customExceptionHandler(Exception ex) {

    ErrorResponse error = new ErrorResponse();
    error.setMessage(ex.getMessage());
    return new ResponseEntity<ErrorResponse>(error, HttpStatus.SERVICE_UNAVAILABLE);

  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {

    ErrorResponse error = new ErrorResponse();
    error.setMessage(ex.getMessage());
    return new ResponseEntity<ErrorResponse>(error, HttpStatus.SERVICE_UNAVAILABLE);

  }

}

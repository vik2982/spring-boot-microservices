package com.va.books.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

  private static final Logger logger = LogManager.getLogger(GlobalControllerExceptionHandler.class);

  @ExceptionHandler(BookException.class)
  public ResponseEntity<ErrorResponse> customExceptionHandler(Exception ex) {
    logger.error(ex);

    ErrorResponse error = new ErrorResponse();
    error.setMessage(ex.getMessage());
    return new ResponseEntity<ErrorResponse>(error, HttpStatus.SERVICE_UNAVAILABLE);

  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
    logger.error(ex);

    ErrorResponse error = new ErrorResponse();
    error.setMessage("Internal Server Error");
    return new ResponseEntity<ErrorResponse>(error, HttpStatus.SERVICE_UNAVAILABLE);

  }

}

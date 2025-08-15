package com.va.books.exception;

public class BookException extends Exception {

  private String errorMessage;

  public String getErrorMessage() {
    return errorMessage;
  }

  public BookException(String errorMessage) {
    super(errorMessage);
    this.errorMessage = errorMessage;
  }

}

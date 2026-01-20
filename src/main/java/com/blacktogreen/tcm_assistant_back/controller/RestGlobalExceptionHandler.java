package com.blacktogreen.tcm_assistant_back.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestGlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleBadRequest(IllegalArgumentException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleInternalError(Exception ex) {

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error"));
  }

  public static class ErrorResponse {
    private final int status;
    private final String message;

    public ErrorResponse(int status, String message) {
      this.status = status;
      this.message = message;
    }

    public int getStatus() {
      return status;
    }

    public String getMessage() {
      return message;
    }
  }
}

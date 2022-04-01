package com.phone.book.entity;

import javax.servlet.ServletException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExpireTokenHandler {



  @ExceptionHandler(value = { ServletException.class })
  public ResponseEntity servletException(ServletException e) {
    String message = e.getMessage();
    HttpStatus httpStatus = HttpStatus.FORBIDDEN;
    if (message.equals("token_expired")) {
      httpStatus = HttpStatus.UNAUTHORIZED;
      message = "the token is expired and not valid anymore";
    }
    RestErrorResponse restErrorResponse = new RestErrorResponse(httpStatus, message);
    return ResponseEntity.status(httpStatus).body(restErrorResponse);
  }
}
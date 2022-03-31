package com.phone.book.entity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserNotExistsError {
	
	  @ExceptionHandler(Exception.class) public ResponseEntity<Map<String,
	  String>> handleException( Exception e) throws IOException { Map<String,
	  String> errorResponse = new HashMap<>(); errorResponse.put("message",
	  e.getLocalizedMessage()); errorResponse.put("status",
	  HttpStatus.INTERNAL_SERVER_ERROR.toString());
	  
	  return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	 
}

package com.phone.book.entity;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomResponseErrorHandler {

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Map<String, String>> handleException(
	        HttpRequestMethodNotSupportedException e) throws IOException {
	    Map<String, String> errorResponse = new HashMap<>();
	    String provided = e.getMethod();
	    List<String> supported = Arrays.asList(e.getSupportedMethods());

	    String error = provided + " is not one of the supported Http Methods (" +
	            String.join(", ", supported) + ")";
	    errorResponse.put("error", error);
	    errorResponse.put("message", e.getLocalizedMessage());
	    errorResponse.put("status", HttpStatus.METHOD_NOT_ALLOWED.toString());

	    return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleException(
	        Exception e) throws IOException {
	    Map<String, String> errorResponse = new HashMap<>();
	    errorResponse.put("message", e.getLocalizedMessage());
	    errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.toString());

	    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

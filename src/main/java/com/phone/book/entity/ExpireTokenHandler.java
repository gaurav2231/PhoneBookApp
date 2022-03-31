package com.phone.book.entity;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.ServiceUnavailableException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExpireTokenHandler {
	@ExceptionHandler(ServiceUnavailableException.class)
	public ResponseEntity<Map<String, String>> handleException( ServiceUnavailableException e) throws AccessDeniedException {
	    Map<String, String> errorResponse = new HashMap<>();

	    errorResponse.put("message", e.getLocalizedMessage());
	    errorResponse.put("status", HttpStatus.GATEWAY_TIMEOUT.toString());

	    return new ResponseEntity<>(errorResponse, HttpStatus.GATEWAY_TIMEOUT);
		
		
	}
}

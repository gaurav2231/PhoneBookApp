package com.phone.book.entity;

import java.io.IOException;
import java.util.ArrayList;
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
public class InvalidMethodApiErrorHandler {
	
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Object> handleException(
	        HttpRequestMethodNotSupportedException e) throws IOException {
        List<Object> obj=new ArrayList<>();

	    RegisterResponse res =new RegisterResponse();
	    
	    res.setCode(405);
	    Integer a = res.getCode();
	    res.setStatusCode(405);
	    Integer b = res.getStatusCode();
	    Map<String, Object> cust = new HashMap<>();  

	    Map<String, List<String>> body = new HashMap<>();


	    Map<String, String> errorResponse = new HashMap<>();
	    String provided = e.getMethod();
	    List<String> supported = Arrays.asList(e.getSupportedMethods());

	    String error = provided + " is not one of the supported Http Methods (" +
	            String.join(", ", supported) + ")";
	    errorResponse.put("error", error);
	    errorResponse.put("message", e.getLocalizedMessage());
	    errorResponse.put("status", HttpStatus.METHOD_NOT_ALLOWED.toString());
	    cust.put("Code", a);
	    cust.put("StatusCode", b);
	  //  cust.put("message", message);
	    
	    //body.putAll(error);


	    obj.add(errorResponse);
	    obj.add(cust);
	    
	    return new ResponseEntity<>(obj, HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	
	
}

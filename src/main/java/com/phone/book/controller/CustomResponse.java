package com.phone.book.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.phone.book.entity.RegisterResponse;


@ControllerAdvice
public class CustomResponse extends ResponseEntityExceptionHandler{

	
	@Override
	  protected ResponseEntity<Object> handleMethodArgumentNotValid(
	            MethodArgumentNotValidException ex, HttpHeaders headers,HttpStatus status, WebRequest request) {
    RegisterResponse res =new RegisterResponse();
    
        List<Object> obj=new ArrayList<>();

    res.setCode(400);
    Integer a = res.getCode();
    res.setStatusCode(400);
    Integer b = res.getStatusCode();
    
    List<String> message =ex.getFieldErrors().stream()	       
    		.map(DefaultMessageSourceResolvable::getDefaultMessage)
	        .collect(Collectors.toList());
    
    Map<String, Object> cust = new HashMap<>();  
    
    Map<String, List<String>> body = new HashMap<>();
    
    List<String> errors = ex.getBindingResult()
	        .getFieldErrors()
	        .stream()
	        .map(DefaultMessageSourceResolvable::getDefaultMessage)
	        .collect(Collectors.toList());

	  
    cust.put("Code ", a);
    cust.put("StatusCode ", b);
    cust.put("message", message);
    cust.put("data", body);
    body.put("errors", errors);

  obj.add(cust);

	    return new ResponseEntity<>(obj, HttpStatus.BAD_REQUEST);
	  }
}

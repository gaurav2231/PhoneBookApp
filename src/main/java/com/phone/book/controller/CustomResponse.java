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
	      MethodArgumentNotValidException ex, HttpHeaders headers,
	      HttpStatus status, WebRequest request) {
		
		        System.out.println("inside api");          
    RegisterResponse res =new RegisterResponse();
    res.setCode(400);
    Integer a = res.getCode();
    res.setStatusCode(400);
    Integer b = res.getStatusCode();
    Map<String, Object> cust = new HashMap<>();    

    List<String> er =ex.getFieldErrors().stream()	       
    		.map(DefaultMessageSourceResolvable::getDefaultMessage)
	        .collect(Collectors.toList());
    
    cust.put("message", er);
    cust.put("Code ", a);
    cust.put("StatusCode ", b);
	    Map<String, List<String>> body = new HashMap<>();
	    

	    List<String> errors = ex.getBindingResult()
	        .getFieldErrors()
	        .stream()
	        .map(DefaultMessageSourceResolvable::getDefaultMessage)
	        .collect(Collectors.toList());

	    body.put("errors", errors);
	    Map<String,Object> data=new HashMap<>();
	    data.put("data", body);
	    
List<Object> obj=new ArrayList<>();

obj.add(cust);
obj.add(data);
	    return new ResponseEntity<>(obj, HttpStatus.BAD_REQUEST);
	  }
}

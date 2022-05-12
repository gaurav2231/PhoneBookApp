package com.phone.book.ErrorController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.phone.book.customException.EmptyInputException;

@ControllerAdvice
public class ErrorController {

	@ExceptionHandler(EmptyInputException.class)
	public ResponseEntity<String> handleEmptyInput(EmptyInputException emptyInputException){
		
	return new ResponseEntity<String>("Input field is empty, please provide",HttpStatus.BAD_REQUEST);
	}
	
}

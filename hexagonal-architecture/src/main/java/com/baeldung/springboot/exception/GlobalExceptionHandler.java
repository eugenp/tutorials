package com.baeldung.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(PersonException.class)
	public ResponseEntity<ErrorResponse> handleException(PersonException personException) {
		
		String code = personException.getCode();
		ErrorResponse errorResponse = new ErrorResponse(personException.getId(), code, personException.getMessage());
		switch (code) {
		
			case  "404" :
				return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
				
			case "400" :
				return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
						
			default :
				errorResponse.setCode("500");
				errorResponse.setMessage("Internal Server Error");
				return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
			
	}
	
}

package com.baeldung.hexagonal.controller;

import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.baeldung.hexagonal.erros.ConflictError;
import com.baeldung.hexagonal.exceptions.TimeSheetConflictException;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler{
	
	private Logger LOG = Logger.getLogger(ExceptionController.class.getName());

	@ExceptionHandler(value = TimeSheetConflictException.class )
	public ResponseEntity<ConflictError> getNotfoundException( TimeSheetConflictException ex, WebRequest request){
		LOG.warning(ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ConflictError.builder().code(HttpStatus.CONFLICT.value())
																					  .message(ex.getMessage())
																					  .build());
	}
	
}
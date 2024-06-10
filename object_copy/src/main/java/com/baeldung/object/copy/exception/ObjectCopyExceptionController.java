package com.baeldung.object.copy.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("com.baeldung.object.copy.rest")
public class ObjectCopyExceptionController {

	private Logger logger = LoggerFactory.getLogger(ObjectCopyExceptionController.class);

	@ExceptionHandler(value = { InvalidCopyTypeException.class })
	public ResponseEntity<String> handleException(InvalidCopyTypeException e) {
		logger.error(e.getMessage());
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
	}
}

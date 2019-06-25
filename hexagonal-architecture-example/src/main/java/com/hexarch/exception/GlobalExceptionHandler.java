package com.hexarch.exception;

import com.hexarch.constants.ItemConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<FieldError> fieldError = ex.getBindingResult().getFieldErrors();
        List<String> errorFields = new ArrayList<>();
        for (FieldError error : fieldError) {
            errorFields.add(error.getField());
        }
        String errorMsg = ItemConstants.ERROR_MESSAGE + errorFields.toString();
        log.info("ErrorMessage : " + errorMsg);

        return new ResponseEntity<>(errorMsg, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<?> errorHandler(Exception ex) {
        log.error("Exception Occured and the exception is : " + ex);
        return new ResponseEntity<>("Run time Exception Occured", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

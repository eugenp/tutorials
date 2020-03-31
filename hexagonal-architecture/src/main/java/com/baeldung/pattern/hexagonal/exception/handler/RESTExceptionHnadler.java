package com.baeldung.pattern.hexagonal.exception.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.baeldung.pattern.hexagonal.exception.RESTException;
import com.baeldung.pattern.hexagonal.exception.dto.ErrorResponseDTO;

public class RESTExceptionHnadler {

    @ExceptionHandler({ RESTException.class })
    public ResponseEntity<ErrorResponseDTO> RestExceptionHandler(RESTException rex) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(new ErrorResponseDTO(rex.getErrorCode(), rex.getMessage()), headers, rex.getHttpStatus());
    }

}

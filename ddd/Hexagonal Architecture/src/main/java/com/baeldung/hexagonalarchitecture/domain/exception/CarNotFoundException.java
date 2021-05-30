package com.baeldung.hexagonalarchitecture.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Car Not Found")
public class CarNotFoundException extends RuntimeException {

}

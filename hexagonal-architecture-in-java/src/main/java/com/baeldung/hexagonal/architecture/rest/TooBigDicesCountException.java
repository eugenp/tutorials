package com.baeldung.hexagonal.architecture.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TooBigDicesCountException extends RuntimeException {
    //
}

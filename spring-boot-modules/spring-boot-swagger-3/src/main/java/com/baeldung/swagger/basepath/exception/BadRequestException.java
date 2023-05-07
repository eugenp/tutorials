package com.baeldung.swagger.basepath.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
  private static final long serialVersionUID = 6171978443681353425L;

  public BadRequestException(String message) {
    super(message);
  }
}

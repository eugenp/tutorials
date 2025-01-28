package com.baeldung.caching.ttl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ElementNotFoundException extends RuntimeException {
  private static final long serialVersionUID = -5218143265247846948L;

  public ElementNotFoundException(String message) {
    super(message);
  }
}
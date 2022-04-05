package com.baeldung.web.reports;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ExportingErrorException extends RuntimeException {

  private static final long serialVersionUID = 4075788919321977605L;

  public ExportingErrorException() {
    super("Error while trying to export data to file.");
  }

  public ExportingErrorException(String message) {
    super(message);
  }

}

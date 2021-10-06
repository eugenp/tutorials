package com.account.service.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.text.MessageFormat;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@AllArgsConstructor
@Getter
public enum ErrorCode {
  INVALID_REQUEST(BAD_REQUEST, "Invalid client request : {0}"),
  INTERNAL_ERROR(INTERNAL_SERVER_ERROR, "Invalid server error"),
  ACCOUNT_NOT_FOUND(NOT_FOUND, "Account is not exist id : {0}"),
  SENDER_ACCOUNT_NOT_FOUND(NOT_FOUND, "Sender Account is not exist id : {0}"),
  RECEIVER_ACCOUNT_NOT_FOUND(NOT_FOUND, "Receiver Account is not exist id : {0}"),
  NOT_SUFFICIENT_BALANCE(BAD_REQUEST, "Sender has not enough money in account : {0}");

  private HttpStatus status;
  private String message;

  public ErrorResultException asErrorResult(final Object... params) {
    return ErrorResultException.builder()
      .code(name())
      .type(status.name())
      .status(status.value())
      .message(MessageFormat.format(message, params))
      .build();
  }
}

package com.account.service.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
@Builder
public class ErrorResultException extends RuntimeException {
  private String type;
  private String code;
  private String message;
  private int status;
}

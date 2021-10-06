package com.account.service.api.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.time.Instant;


@Value
@Builder
public class AccountRequest {
  @NotNull
  private String accountId;
  @NotNull
  private String name;
  @NotNull
  private String owner;
  private BigInteger balance;

}

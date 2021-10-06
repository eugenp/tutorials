package com.account.service.service.command;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Value
@Builder
public class CreateAccountCommand {
  @NotNull
  private String accountId;

  @NotNull
  private String name;

  @NotNull
  private String owner;

  private BigInteger balance;
}

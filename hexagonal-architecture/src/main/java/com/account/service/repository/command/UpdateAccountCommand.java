package com.account.service.repository.command;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.math.BigInteger;

@Value
@Builder
@With
public class UpdateAccountCommand {
  private Long id;
  private String accountId;
  private String name;
  private String owner;
  private BigInteger balance;
}

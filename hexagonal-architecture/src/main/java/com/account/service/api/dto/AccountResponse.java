package com.account.service.api.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigInteger;
import java.time.Instant;


@Value
@Builder
public class AccountResponse {
  private Long id;
  private String accountId;
  private String name;
  private String owner;
  private BigInteger balance;
  private Instant createdAt;

}

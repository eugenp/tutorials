package com.account.service.api.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Value
@Builder
public class MoneyTransferRequest {
  @NotNull
  private String senderAccountId;
  @NotNull
  private String receiverAccountId;
  @NotNull
  private BigInteger amount;
}

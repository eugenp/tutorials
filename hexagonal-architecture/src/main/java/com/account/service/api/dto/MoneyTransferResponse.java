package com.account.service.api.dto;

import com.account.service.domain.TransferResult;
import lombok.Builder;
import lombok.Value;

import java.math.BigInteger;
import java.time.Instant;

@Value
@Builder
public class MoneyTransferResponse {
  private Long id;
  private String senderAccountId;
  private String receiverAccountId;
  private BigInteger amount;
  private TransferResult result;
  private Instant timeStamp;
}

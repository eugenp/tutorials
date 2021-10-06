package com.account.service.domain;


import lombok.Builder;
import lombok.Value;

import java.math.BigInteger;
import java.time.Instant;

@Value
@Builder
public class Transfer {
  private Long id;
  private String senderAccountId;
  private String receiverAccountId;
  private BigInteger amount;
  private TransferResult result;
  private String detail;
  private Instant createdAt;
}

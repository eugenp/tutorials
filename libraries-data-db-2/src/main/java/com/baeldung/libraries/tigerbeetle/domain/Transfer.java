package com.baeldung.libraries.tigerbeetle.domain;

import lombok.Builder;

import java.math.BigInteger;
import java.time.Instant;
import java.util.UUID;

@Builder
public record Transfer(
  UUID id,
  BigInteger amount,
  int code,
  int ledger,
  int flags,
  UUID debitAccountId,
  UUID creditAccountId,
  Instant timestamp,
  int userData32,
  long userData64,
  UUID userData128,
  UUID pendingId
) {
}

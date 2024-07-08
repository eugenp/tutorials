package com.baeldung.libraries.tigerbeetle.domain;

import lombok.Builder;

import java.math.BigInteger;
import java.time.Instant;
import java.util.UUID;

@Builder
public record Balance(
  UUID accountId,
  Instant timestamp,
  BigInteger creditsPosted,
  BigInteger creditsPending,
  BigInteger debitsPosted,
  BigInteger debitsPending
) {
}

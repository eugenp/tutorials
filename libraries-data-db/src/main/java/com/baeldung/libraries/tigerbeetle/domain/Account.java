package com.baeldung.libraries.tigerbeetle.domain;

import lombok.Builder;

import java.math.BigInteger;
import java.util.UUID;

@Builder
public record Account(
    UUID id,
    BigInteger accountHolderId,
    int code,
    int ledger,
    BigInteger creditsPosted,
    BigInteger creditsPending,
    BigInteger debtsPosted,
    BigInteger debtsPending,
    int flags,
    long timestamp) {}

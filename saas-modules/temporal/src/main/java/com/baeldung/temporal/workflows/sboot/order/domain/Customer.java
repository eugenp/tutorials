package com.baeldung.temporal.workflows.sboot.order.domain;

import java.time.LocalDate;
import java.util.UUID;

public record Customer(
  UUID uuid,
  String name,
  LocalDate birthDate
) {
}

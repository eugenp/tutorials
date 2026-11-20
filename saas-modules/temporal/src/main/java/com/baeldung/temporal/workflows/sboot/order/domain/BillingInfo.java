package com.baeldung.temporal.workflows.sboot.order.domain;

import java.math.BigDecimal;

public record BillingInfo(
  String cardToken,
  BigDecimal amount,
  String currency
) {
}

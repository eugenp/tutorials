package com.baeldung.temporal.workflows.sboot.order.domain;

import java.math.BigDecimal;

public record PaymentAuthorization(
  BillingInfo info,
  PaymentStatus status,
  String orderId,
  String authorizationId) {
}

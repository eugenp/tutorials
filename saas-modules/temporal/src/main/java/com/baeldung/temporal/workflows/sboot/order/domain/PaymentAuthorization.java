package com.baeldung.temporal.workflows.sboot.order.domain;

public record PaymentAuthorization(
  BillingInfo info,
  PaymentStatus status,
  String orderId,
  String transactionId,
  String authorizationId,
  String cause) {
}

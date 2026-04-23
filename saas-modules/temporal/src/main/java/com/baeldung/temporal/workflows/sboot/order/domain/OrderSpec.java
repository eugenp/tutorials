package com.baeldung.temporal.workflows.sboot.order.domain;

public record OrderSpec(
  Order order,
  BillingInfo billingInfo,
  ShippingInfo shippingInfo,
  Customer customer) {}

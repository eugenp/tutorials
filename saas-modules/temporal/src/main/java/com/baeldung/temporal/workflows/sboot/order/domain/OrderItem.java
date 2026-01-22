package com.baeldung.temporal.workflows.sboot.order.domain;

public record OrderItem(
  String sku,
  int quantity
) {

}

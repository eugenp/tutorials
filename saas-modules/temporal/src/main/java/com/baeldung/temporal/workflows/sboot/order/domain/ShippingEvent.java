package com.baeldung.temporal.workflows.sboot.order.domain;

import java.time.Instant;

public record ShippingEvent(
  Instant ts,
  ShippingStatus status,
  String comment
) {
}

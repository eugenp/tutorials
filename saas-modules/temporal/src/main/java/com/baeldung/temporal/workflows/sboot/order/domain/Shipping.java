package com.baeldung.temporal.workflows.sboot.order.domain;

import org.springframework.util.Assert;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public record Shipping(
    Order order,
    ShippingProvider provider,
    ShippingStatus status,
    List<ShippingEvent> history
    ) {

    public Shipping toStatus(ShippingStatus newStatus, Instant ts, String comment) {
        return new Shipping(
          order(),
          provider(),
          newStatus,
          append(history, newStatus, ts, comment)
        );
    }

    private static List<ShippingEvent> append(List<ShippingEvent> history, ShippingStatus status, Instant ts, String comment) {
        return List.of(history,List.of(new ShippingEvent(ts, status, comment)))
          .stream()
          .flatMap(List::stream)
          .collect(Collectors.toUnmodifiableList());
    }
}

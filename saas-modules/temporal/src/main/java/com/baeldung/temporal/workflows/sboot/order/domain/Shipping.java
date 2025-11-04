package com.baeldung.temporal.workflows.sboot.order.domain;

import java.util.List;

public record Shipping(
    Order order,
    ShippingProvider provider,
    ShippingStatus status,
    List<ShippingEvent> history
    ) {
}

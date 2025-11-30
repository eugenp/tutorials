package com.baeldung.temporal.workflows.sboot.order.domain;

import java.util.List;
import java.util.UUID;

public record Order(UUID orderId, List<OrderItem> items) {
}

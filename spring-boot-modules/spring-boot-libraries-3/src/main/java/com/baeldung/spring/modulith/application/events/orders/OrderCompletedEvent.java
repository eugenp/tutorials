package com.baeldung.spring.modulith.application.events.orders;

import java.time.Instant;

public record OrderCompletedEvent(String orderId, String customerId, Instant timestamp) {
}

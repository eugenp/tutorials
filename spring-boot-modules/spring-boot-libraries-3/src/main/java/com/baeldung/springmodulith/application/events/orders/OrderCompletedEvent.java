package com.baeldung.springmodulith.application.events.orders;

import java.time.Instant;

public record OrderCompletedEvent(String orderId, String customerId, Instant timestamp) {
}

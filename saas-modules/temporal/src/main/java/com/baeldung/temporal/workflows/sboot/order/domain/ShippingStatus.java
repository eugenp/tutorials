package com.baeldung.temporal.workflows.sboot.order.domain;

public enum ShippingStatus {
    CREATED,
    WAITING_FOR_PICKUP,
    SHIPPED,
    DELIVERED,
    RETURNED,
    CANCELLED,
}

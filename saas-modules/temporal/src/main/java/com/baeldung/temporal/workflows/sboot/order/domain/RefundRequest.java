package com.baeldung.temporal.workflows.sboot.order.domain;

public record RefundRequest(PaymentAuthorization payment) {
}

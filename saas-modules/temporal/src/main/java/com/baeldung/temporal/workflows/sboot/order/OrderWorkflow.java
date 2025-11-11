package com.baeldung.temporal.workflows.sboot.order;

import com.baeldung.temporal.workflows.sboot.order.domain.*;
import io.temporal.workflow.*;

import java.time.Instant;

@WorkflowInterface
public interface OrderWorkflow {

    @WorkflowMethod
    void processOrder(OrderSpec spec);

    @SignalMethod
    void paymentAuthorized(String transactionId, String authorizationId);

    @SignalMethod
    void paymentDeclined(String transactionId, String cause);

    @UpdateMethod
    void packagePickup(Instant pickupTime);

    @SignalMethod
    void packageDelivered(Instant pickupTime);

    @SignalMethod
    void packageReturned(Instant pickupTime);

    @QueryMethod
    Order getOrder();

    @QueryMethod
    Shipping getDelivery();

    @QueryMethod
    PaymentAuthorization getPayment();

    @QueryMethod
    RefundRequest getRefund();

}

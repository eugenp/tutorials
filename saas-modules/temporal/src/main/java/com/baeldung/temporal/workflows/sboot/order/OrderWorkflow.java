package com.baeldung.temporal.workflows.sboot.order;

import com.baeldung.temporal.workflows.sboot.order.domain.*;
import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

import java.time.Instant;

@WorkflowInterface
public interface OrderWorkflow {

    @WorkflowMethod
    void processOrder(OrderSpec spec);

    @SignalMethod
    void paymentAuthorized(String transactionId);

    @SignalMethod
    void paymentDeclined(String cause);

    @SignalMethod
    void packagePickup(Instant pickupTime);

    @SignalMethod
    void packageDelivered(Instant pickupTime);

    @SignalMethod
    void packageReturned(Instant pickupTime);

    @QueryMethod
    Order getOrder();

    @QueryMethod
    Shipping getDelivery();

}

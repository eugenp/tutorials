package com.baeldung.temporal.workflows.sboot.order.activities;

import com.baeldung.temporal.workflows.sboot.order.domain.*;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface OrderActivities {

    void reserveOrderItems(Order order);
    void cancelReservedItems(Order order);
    void returnOrderItems(Order order);
    void dispatchOrderItems(Order order);

    PaymentAuthorization createPaymentRequest(Order order, BillingInfo billingInfo);
    RefundRequest createRefundRequest(PaymentAuthorization payment);

    Shipping createShipping(Order order);
    Shipping updateShipping(Shipping shipping, ShippingStatus status);
}

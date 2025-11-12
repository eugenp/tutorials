package com.baeldung.temporal.workflows.sboot.order;

import com.baeldung.temporal.workflows.sboot.order.activities.OrderActivities;
import com.baeldung.temporal.workflows.sboot.order.domain.*;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

@Service
@WorkflowImpl(taskQueues = "ORDERS")
public class OrderWorkflowImpl implements OrderWorkflow {

    private static final Logger log = LoggerFactory.getLogger(OrderWorkflowImpl.class);


    private Order order;
    private Shipping shipping;
    private final Supplier<OrderActivities> orderActivities;
    private PaymentAuthorization payment;
    private RefundRequest refund;


    public OrderWorkflowImpl() {

        log.info("[I30] OrderWorkflowImpl created");

        orderActivities = () -> Workflow.newActivityStub(OrderActivities.class,
          ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofSeconds(10))
            .setRetryOptions(RetryOptions.newBuilder()
              .setMaximumAttempts(3)
              .setInitialInterval(Duration.ofSeconds(1))
              .build())
            .build()
        );

    }

    @Override
    public void processOrder(OrderSpec spec) {

        log.info("processOrder: spec={}", spec);
        order = spec.order();

        // Reserve inventory
        var activities = orderActivities.get();
        activities.reserveOrderItems(spec.order());

        // Create payment request
        payment = activities.createPaymentRequest(spec.order(), spec.billingInfo());

        // Create a shipping request
        shipping = activities.createShipping(spec.order());

        // Wait for a payment result, which will be triggered by one of the signal methods
        Workflow.await(() -> payment.status() != PaymentStatus.PENDING);

        // Process payment result
        if ( payment.status() == PaymentStatus.DECLINED) {
            log.info("[I75] Payment declined");
            activities.cancelReservedItems(spec.order());
            refund = activities.createRefundRequest(payment);
            return;
        }

        log.info("[I76] Payment approved. Starting shipping");
        shipping = activities.updateShipping(shipping, ShippingStatus.WAITING_FOR_PICKUP);

        // Wait at most one day for package pickup
        if ( !Workflow.await(Duration.ofDays(1), () -> shipping.status() == ShippingStatus.SHIPPED)) {
            log.info("[I86] Package not picked up");
            shipping = activities.updateShipping(shipping, ShippingStatus.CANCELLED);
            activities.cancelReservedItems(spec.order());
            refund = activities.createRefundRequest(payment);
            return;
        }

        // The items left the warehouse
        activities.dispatchOrderItems(spec.order());

        // Wait up to a week for delivery completion
        if ( !Workflow.await(Duration.ofDays(7), () -> checkShippingCompleted())) {
            log.info("[I87] Delivery timeout. Assuming package lost...");
            shipping = activities.updateShipping(shipping, ShippingStatus.CANCELLED);
            activities.reserveOrderItems(spec.order());
        }
        else if (shipping.status() == ShippingStatus.RETURNED){
            // Package returned. Add items back to inventory
            activities.returnOrderItems(order);
            refund = activities.createRefundRequest(payment);
        }
        else {
            log.info("[I90] Shipping completed");
            // Package delivered. Update shipping status
            shipping = activities.updateShipping(shipping, ShippingStatus.DELIVERED);
        }

        log.info("[I102] Order completed");
    }

    private boolean checkShippingCompleted() {
        return shipping.status() == ShippingStatus.DELIVERED || shipping.status() == ShippingStatus.RETURNED;
    }

    @Override
    public void paymentAuthorized(String transactionId, String authorizationId) {
        Workflow.await(() -> payment != null);
        payment = new PaymentAuthorization(
          payment.info(),
          PaymentStatus.APPROVED,
          payment.orderId(),
          transactionId,
          authorizationId,
          null
        );
    }

    @Override
    public void paymentDeclined(String transactionId, String cause) {
        Workflow.await(() -> payment != null);
        payment = new PaymentAuthorization(
          payment.info(),
          PaymentStatus.DECLINED,
          payment.orderId(),
          transactionId,
          null,
          cause
        );

    }

    @Override
    public void packagePickup(Instant pickupTime) {
        Workflow.await(() -> shipping != null);
        shipping = shipping.toStatus(ShippingStatus.SHIPPED, pickupTime, "Package picked up");
    }

    @Override
    public void packageDelivered(Instant pickupTime) {
        Workflow.await(() -> shipping != null);
        shipping = shipping.toStatus(ShippingStatus.DELIVERED, pickupTime, "Package delivered");
    }

    @Override
    public void packageReturned(Instant pickupTime) {
        shipping = shipping.toStatus(ShippingStatus.RETURNED, pickupTime, "Package returned");
    }

    @Override
    public Order getOrder() {
        return order;
    }

    @Override
    public Shipping getShipping() {
        return shipping;
    }

    @Override
    public PaymentAuthorization getPayment() {
        return payment;
    }

    @Override
    public RefundRequest getRefund() {
        return refund;
    }

}

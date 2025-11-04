package com.baeldung.temporal.workflows.sboot.order.activities;

import com.baeldung.temporal.workflows.sboot.order.domain.*;
import io.temporal.spring.boot.ActivityImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.List;
import java.util.UUID;

@Service
@ActivityImpl
public class OrderActivitiesImpl implements OrderActivities{
    private static  final Logger log = LoggerFactory.getLogger(OrderActivitiesImpl.class);

    private final Clock clock;

    public OrderActivitiesImpl(Clock clock) {
        this.clock = clock;
    }

    @Override
    public void reserveOrderItems(Order order) {
        log.info("reserveOrderItems: orderId={}", order.orderId());
    }

    @Override
    public void cancelReservedItems(Order order) {

    }

    @Override
    public void returnReservedItems(Order order) {

    }


    @Override
    public PaymentAuthorization createPaymentRequest(Order order, BillingInfo billingInfo) {
        return new PaymentAuthorization(
          billingInfo,
          PaymentStatus.PENDING,
          order.orderId().toString(),
          UUID.randomUUID().toString());
    }

    @Override
    public RefundRequest createRefundRequest(PaymentAuthorization payment) {
        return null;
    }

    @Override
    public Shipping createShipping(Order order) {

        var provider = selectProvider(order);

        return new Shipping(
          order,
          provider,
          ShippingStatus.PENDING,
          List.of(new ShippingEvent(null, ShippingStatus.PENDING, "Shipping created")));
    }

    private ShippingProvider selectProvider(Order order) {

        int totalItems = order.items().stream()
          .map(OrderItem::quantity)
          .reduce(0, Integer::sum);

        if ( totalItems < 5) {
            return ShippingProvider.DHL;
        }
        else if ( totalItems < 10) {
            return ShippingProvider.FedEx;
        }
        else {
            return ShippingProvider.UPS;
        }

    }

    @Override
    public Shipping updateShipping(Shipping shipping, ShippingStatus status) {
        return null;
    }
}

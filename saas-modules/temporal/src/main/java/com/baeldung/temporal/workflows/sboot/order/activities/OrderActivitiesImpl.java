package com.baeldung.temporal.workflows.sboot.order.activities;

import com.baeldung.temporal.workflows.sboot.order.domain.*;
import com.baeldung.temporal.workflows.sboot.order.services.InventoryService;
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
    private final InventoryService inventoryService;

    public OrderActivitiesImpl(Clock clock, InventoryService inventoryService) {
        this.clock = clock;
        this.inventoryService = inventoryService;
        log.info("[I22] OrderActivitiesImpl created");
    }

    @Override
    public void reserveOrderItems(Order order) {
        log.info("reserveOrderItems: order={}", order);
        for (OrderItem item : order.items()) {
            inventoryService.reserveInventory(item.sku(), item.quantity());
        }
    }

    @Override
    public void cancelReservedItems(Order order) {
        log.info("cancelReservedItems: order={}", order);
        for (OrderItem item : order.items()) {
            inventoryService.reserveInventory(item.sku(), item.quantity());
        }
    }

    @Override
    public void returnOrderItems(Order order) {

    }


    @Override
    public PaymentAuthorization createPaymentRequest(Order order, BillingInfo billingInfo) {
        return new PaymentAuthorization(
          billingInfo,
          PaymentStatus.PENDING,
          order.orderId().toString(),
          UUID.randomUUID().toString(),
          null,
          null);
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
          ShippingStatus.CREATED,
          List.of(new ShippingEvent(
            clock.instant(),
            ShippingStatus.CREATED,
            "Shipping created")));
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

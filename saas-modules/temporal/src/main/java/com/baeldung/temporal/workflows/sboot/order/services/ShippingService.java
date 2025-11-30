package com.baeldung.temporal.workflows.sboot.order.services;

import com.baeldung.temporal.workflows.sboot.order.domain.*;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.List;

@Service
public class ShippingService {
    private final Clock clock;

    public ShippingService(Clock clock) {
        this.clock = clock;
    }

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

    public Shipping updateStatus(Shipping shipping, ShippingStatus status) {
        return shipping.toStatus(status, clock.instant(), "Shipping status updated");
    }
}

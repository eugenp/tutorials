package com.baeldung.dddcontexts.ordercontext.service;

import com.baeldung.dddcontexts.ordercontext.model.CustomerOrder;
import com.baeldung.dddcontexts.ordercontext.repository.CustomerOrderRepository;
import com.baeldung.dddcontexts.sharedkernel.events.ApplicationEvent;
import com.baeldung.dddcontexts.sharedkernel.events.EventBus;

import java.util.HashMap;
import java.util.Map;

public class CustomerOrderService implements OrderService {
    public static final String EVENT_ORDER_READY_FOR_SHIPMENT = "OrderReadyForShipmentEvent";

    private CustomerOrderRepository orderRepository;
    private EventBus eventBus;

    @Override
    public void placeOrder(CustomerOrder order) {
        this.orderRepository.saveCustomerOrder(order);
        Map<String, String> payload = new HashMap<>();
        payload.put("order_id", String.valueOf(order.getOrderId()));
        ApplicationEvent event = new ApplicationEvent(payload) {
            @Override
            public String getType() {
                return EVENT_ORDER_READY_FOR_SHIPMENT;
            }
        };
        this.eventBus.publish(event);
    }

    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

    public void setOrderRepository(CustomerOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }
}

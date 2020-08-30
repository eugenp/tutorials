package com.baeldung.dddmodules.mainapp;

import com.baeldung.dddmodules.ordercontext.model.CustomerOrder;
import com.baeldung.dddmodules.ordercontext.model.OrderItem;
import com.baeldung.dddmodules.ordercontext.repository.CustomerOrderRepository;
import com.baeldung.dddmodules.ordercontext.service.OrderService;
import com.baeldung.dddmodules.sharedkernel.events.EventBus;
import com.baeldung.dddmodules.shippingcontext.repository.ShippingOrderRepository;
import com.baeldung.dddmodules.shippingcontext.service.ShippingService;

import java.util.*;

public class Application {

    public static void main(String args[]) {
        Map<Class<?>, Object> container = createContainer();
        OrderService orderService = (OrderService) container.get(OrderService.class);
        ShippingService shippingService = (ShippingService) container.get(ShippingService.class);
        shippingService.listenToOrderEvents();

        CustomerOrder customerOrder = new CustomerOrder();
        int orderId = 1;
        customerOrder.setOrderId(orderId);
        List<OrderItem> orderItems = new ArrayList<OrderItem>();
        orderItems.add(new OrderItem(1, 2, 3, 1));
        orderItems.add(new OrderItem(2, 1, 1, 1));
        orderItems.add(new OrderItem(3, 4, 11, 21));
        customerOrder.setOrderItems(orderItems);
        customerOrder.setPaymentMethod("PayPal");
        customerOrder.setAddress("Full address here");
        orderService.placeOrder(customerOrder);

        if (orderId == shippingService.getParcelByOrderId(orderId).get().getOrderId()) {
            System.out.println("Order has been processed and shipped successfully");
        }
    }

    public static Map<Class<?>, Object> createContainer() {
        EventBus eventBus = ServiceLoader.load(EventBus.class).findFirst().get();
        CustomerOrderRepository customerOrderRepository = ServiceLoader.load(CustomerOrderRepository.class).findFirst().get();
        ShippingOrderRepository shippingOrderRepository = ServiceLoader.load(ShippingOrderRepository.class).findFirst().get();
        ShippingService shippingService = ServiceLoader.load(ShippingService.class).findFirst().get();
        shippingService.setEventBus(eventBus);
        shippingService.setOrderRepository(shippingOrderRepository);
        OrderService orderService = ServiceLoader.load(OrderService.class).findFirst().get();
        orderService.setEventBus(eventBus);
        orderService.setOrderRepository(customerOrderRepository);
        HashMap<Class<?>, Object> container = new HashMap<>();
        container.put(OrderService.class, orderService);
        container.put(ShippingService.class, shippingService);
        return container;
    }

}

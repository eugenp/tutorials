package com.baeldung.beaninjection.service;

import org.springframework.stereotype.Component;

import com.baeldung.beaninjection.builder.OrderBuilder;
import com.baeldung.beaninjection.model.Customer;
import com.baeldung.beaninjection.model.Offers;
import com.baeldung.beaninjection.model.Order;

@Component
public class OrderService {

    private CustomerService customerService;
    private OffersService offersService;

    public OrderService(CustomerService customerService) {
        // constructor injection, object provided at runtime
        this.customerService = customerService;
    }

    public Order getOrders(int customerId) {
        OrderBuilder orderBuilder = new OrderBuilder();
        Order order = orderBuilder.getOrder(customerId);
        Customer customer = customerService.getCustomer(customerId);
        order.setCustomer(customer);
        if (offersService != null) {// not null check
            Offers additionalOffers = offersService.getOffers(customerId);
            order.setAdditionalOffers(additionalOffers);
        }
        return order;
    }

    // setter injection object provided at runtime
    public void setOffersService(OffersService offersService) {
        this.offersService = offersService;
    }
}

package com.baeldung.web.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.persistence.model.Customer;
import com.baeldung.persistence.model.Order;
import com.baeldung.services.CustomerService;
import com.baeldung.services.OrderService;

@RestController
@RequestMapping(value = "/customers")
@EnableHypermediaSupport(type = HypermediaType.HAL)
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/{customerId}")
    public Customer getCustomerById(@PathVariable final String customerId) {
        return customerService.getCustomerDetail(customerId);
    }

    @GetMapping("/{customerId}/{orderId}")
    public Order getOrderById(@PathVariable final String customerId, @PathVariable final String orderId) {
        return orderService.getOrderByIdForCustomer(customerId, orderId);
    }

    @GetMapping(value = "/{customerId}/orders", produces = { "application/hal+json" })
    public Resources<Order> getOrdersForCustomer(@PathVariable final String customerId) {
        final List<Order> orders = orderService.getAllOrdersForCustomer(customerId);
        for (final Order order : orders) {
            final Link selfLink = linkTo(
                methodOn(CustomerController.class).getOrderById(customerId, order.getOrderId())).withSelfRel();
            order.add(selfLink);
        }

        Link link = linkTo(methodOn(CustomerController.class).getOrdersForCustomer(customerId)).withSelfRel();
        Resources<Order> result = new Resources<>(orders, link);
        return result;
    }

    @GetMapping(produces = { "application/hal+json" })
    public Resources<Customer> getAllCustomers() {
        final List<Customer> allCustomers = customerService.allCustomers();

        for (final Customer customer : allCustomers) {
            String customerId = customer.getCustomerId();
            Link selfLink = linkTo(CustomerController.class).slash(customerId)
                .withSelfRel();
            customer.add(selfLink);
            if (orderService.getAllOrdersForCustomer(customerId)
                .size() > 0) {
                final Link ordersLink = linkTo(methodOn(CustomerController.class).getOrdersForCustomer(customerId))
                    .withRel("allOrders");
                customer.add(ordersLink);
            }
        }

        Link link = linkTo(CustomerController.class).withSelfRel();
        Resources<Customer> result = new Resources<>(allCustomers, link);
        return result;
    }

}

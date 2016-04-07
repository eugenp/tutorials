package org.baeldung.web.controller;

import java.util.List;

import org.baeldung.persistence.model.Customer;
import org.baeldung.persistence.model.Order;
import org.baeldung.web.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/customer/{customerId}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable final String customerId) {
        return customerService.getCustomerDetail(customerId);
    }

    @RequestMapping(value = "/customer/{customerId}/{orderId}", method = RequestMethod.GET)
    public Order getOrderByIdForCustomer(@PathVariable final String customerId, @PathVariable final String orderId) {
        return customerService.getOrderByIdForCustomer(customerId, orderId);
    }

    @RequestMapping(value = "/customer/{customerId}/orders", method = RequestMethod.GET)
    public List<Order> getOrdersForCustomer(@PathVariable final String customerId) {
        return customerService.getAllOrdersForCustomer(customerId);
    }

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public List<Customer> getAllCustomers() {
        final List<Customer> allCustomers = customerService.allCustomers();
        for (final Customer customer : allCustomers) {
            final Link selfLink = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(CustomerController.class).getCustomerById(customer.getCustomerId())).withSelfRel();
            customer.add(selfLink);
            if (customer.getOrders().values().size() > 0) {
                final Link ordersLink = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(CustomerController.class).getOrdersForCustomer(customer.getCustomerId())).withRel("allOrders");
                customer.add(ordersLink);
            }
            for (final Order order : customer.getOrders().values()) {
                final Link orderLink = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(CustomerController.class).getOrderByIdForCustomer(customer.getCustomerId(), order.getOrderId())).withRel("order");
                order.add(orderLink);
            }
        }
        return allCustomers;
    }

}

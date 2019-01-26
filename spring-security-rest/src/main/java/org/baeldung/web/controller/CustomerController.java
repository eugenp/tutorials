package org.baeldung.web.controller;

import org.baeldung.persistence.model.Customer;
import org.baeldung.persistence.model.Order;
import org.baeldung.web.service.CustomerService;
import org.baeldung.web.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable final String customerId) {
        return customerService.getCustomerDetail(customerId);
    }

    @RequestMapping(value = "/{customerId}/{orderId}", method = RequestMethod.GET)
    public Order getOrderById(@PathVariable final String customerId, @PathVariable final String orderId) {
        return orderService.getOrderByIdForCustomer(customerId, orderId);
    }

    @RequestMapping(value = "/{customerId}/orders", method = RequestMethod.GET)
    public List<Order> getOrdersForCustomer(@PathVariable final String customerId) {
        final List<Order> orders = orderService.getAllOrdersForCustomer(customerId);
        for (final Order order : orders) {
            final Link selfLink = linkTo(methodOn(CustomerController.class).getOrderById(customerId, order.getOrderId())).withSelfRel();
            order.add(selfLink);
        }
        return orders;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> getAllCustomers() {
        final List<Customer> allCustomers = customerService.allCustomers();
        for (final Customer customer : allCustomers) {
            String customerId = customer.getCustomerId();
            Link selfLink = linkTo(CustomerController.class).slash(customerId).withSelfRel();
            customer.add(selfLink);
            if (orderService.getAllOrdersForCustomer(customerId).size() > 0) {
                List<Order> methodLinkBuilder = methodOn(CustomerController.class).getOrdersForCustomer(customerId);
                final Link ordersLink = linkTo(methodLinkBuilder).withRel("allOrders");
                customer.add(ordersLink);
            }

        }
        return allCustomers;
    }

}

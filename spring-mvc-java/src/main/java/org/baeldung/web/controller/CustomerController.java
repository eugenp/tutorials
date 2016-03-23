package org.baeldung.web.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.baeldung.model.Customer;
import org.baeldung.model.CustomerListRepresentation;
import org.baeldung.web.service.CustomerMockServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@RestController
public class CustomerController
{
    @Autowired
    private CustomerMockServiceImpl customerMockService;

    @RequestMapping(value = "/customer/{customerId}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable final String customerId) {
        return customerMockService.getCustomerDetail(customerId);
    }

    @RequestMapping(value="/customers", method=RequestMethod.GET)
    public CustomerListRepresentation getAllCustomers() {
        final List<Customer> allCustomers = customerMockService.allCustomers();
        final List<Customer> customerCollection = new ArrayList<Customer>(allCustomers.size());
        for (final Customer customer : allCustomers) {
            final Customer customerRepresentation = new Customer(customer.getCustomerId(), customer.getCustomerName(), customer.getCustomerCompany());
            final Link link = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(CustomerController.class).getCustomerById(customerRepresentation.getCustomerId())).withSelfRel();
            customerRepresentation.add(link);
            customerCollection.add(customerRepresentation);
        }
        return new CustomerListRepresentation(customerCollection);
    }

    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    public ResponseEntity<Customer> createCustomer(@RequestBody final Customer customer) {
        final Customer createdCustomer = customerMockService.createCustomer(customer);
        final HttpHeaders headers = new HttpHeaders();
        final URI uri = MvcUriComponentsBuilder.fromMethodName(CustomerController.class, "getCustomerById", createdCustomer.getCustomerId()).build().toUri();
        headers.setLocation(uri);
        return new ResponseEntity<Customer>(createdCustomer, headers, HttpStatus.CREATED);
    }


}

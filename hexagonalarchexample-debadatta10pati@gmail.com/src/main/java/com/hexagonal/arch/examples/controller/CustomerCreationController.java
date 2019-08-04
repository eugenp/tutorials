package com.hexagonal.arch.examples.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hexagonal.arch.examples.core.domain.CustomerCriteria;
import com.hexagonal.arch.examples.core.interfaces.ICustomerCreationPort;


@RestController
public class CustomerCreationController {

    @Autowired
    private ICustomerCreationPort customerCreationPort;

    @RequestMapping(value = "/createCustomerId", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity createCustomer(@RequestBody CustomerCriteria criteria) {

        try {
            return ResponseEntity.ok(customerCreationPort.createCustomerId(criteria));

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }
}

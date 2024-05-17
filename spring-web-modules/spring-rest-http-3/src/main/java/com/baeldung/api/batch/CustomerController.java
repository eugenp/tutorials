package com.baeldung.api.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/batch")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping(path = "/customers")
    public ResponseEntity<String> customerBatchUpdate(@RequestBody Set<CustomerBatchRequest> customerBatchRequests) {
        Set<Customer> customerProcessed = new HashSet<>();

        customerBatchRequests.forEach(customerBatchRequest -> {
            switch (customerBatchRequest.getBatchType()) {
                case CREATE:
                    customerProcessed.addAll(customerRepository.createCustomers(customerBatchRequest.getCustomerData()));
                    break;
                case UPDATE:
                    customerProcessed.addAll(customerRepository.updateCustomers(customerBatchRequest.getCustomerData()));
                    break;
                case DELETE:
                    customerProcessed.addAll(customerRepository.deleteCustomers(customerBatchRequest.getCustomerData()));
                    break;
                default:
                    break;
            }
        });

        return ResponseEntity.ok("Customer Batch Processed Successfully for customer size " + customerProcessed.size());
    }

    @GetMapping(path = "/customers")
    public ResponseEntity<Collection<Customer>> customerBatchUpdate() {
        return ResponseEntity.ok(customerRepository.getCustomers());
    }
}

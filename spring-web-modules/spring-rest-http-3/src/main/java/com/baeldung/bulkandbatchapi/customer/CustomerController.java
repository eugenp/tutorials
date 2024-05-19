package com.baeldung.bulkandbatchapi.customer;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(@Autowired CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping(path = "/customers")
    public List<Customer> getAllCustomers() {
        return customerRepository.getCustomers();
    }

    @PostMapping(path = "/bulk/customers")
    public List<Customer> bulkCreateCustomers(@RequestBody @Valid @Size(min = 1, max = 20, message = "size should be between 1 and 20")
                                              List<Customer> customers) {
        return customerRepository.createCustomers(customers);
    }

    @PostMapping(path = "/batch/customers")
    public ResponseEntity<String> batchUpdateCustomers(@RequestBody @Valid @Size(min = 1, max = 20, message = "size should be between 1 and 20")
                                                       List<CustomerBatchRequest> customerBatchRequests) {
        customerBatchRequests.forEach(customerBatchRequest -> {
            switch (customerBatchRequest.getBatchOperationType()) {
                case CREATE:
                    customerRepository.createCustomers(customerBatchRequest.getCustomers());
                    break;
                case UPDATE:
                    customerRepository.updateCustomers(customerBatchRequest.getCustomers());
                    break;
                case DELETE:
                    customerRepository.deleteCustomers(customerBatchRequest.getCustomers());
                    break;
                default:
                    break;
            }
        });

        return ResponseEntity.ok("Customer Batch Request is processed");
    }
}

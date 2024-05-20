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

    private final CustomerService customerService;

    public CustomerController(@Autowired CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(path = "/customers")
    public List<Customer> getAllCustomers() {
        return customerService.getCustomers();
    }

    @PostMapping(path = "/bulk/customers")
    public List<Customer> bulkCreateCustomers(@RequestBody @Valid @Size(min = 1, max = 20, message = "size should be between 1 and 20")
                                              List<Customer> customers) {
        return customerService.createCustomers(customers);
    }

    @PostMapping(path = "/batch/customers")
    public ResponseEntity<String> batchProcessCustomers(@RequestBody @Valid @Size(min = 1, max = 20, message = "size should be between 1 and 20")
                                                        List<CustomerBatchRequest> customerBatchRequests) {
        customerBatchRequests.forEach(customerBatchRequest -> customerService.processCustomers(customerBatchRequest.getCustomers(),
                customerBatchRequest.getBatchType()));

        return ResponseEntity.ok("Customer Batch Request is processed");
    }
}

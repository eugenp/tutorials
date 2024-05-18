package com.baeldung.bulkandbatchapi.customer;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@Validated
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(@Autowired CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PutMapping(path = "/customer")
    public Customer updateCustomer(@RequestBody Customer customer) {
        return customerRepository.updateCustomer(customer);
    }

    @GetMapping(path = "/customers")
    public Set<Customer> getAllCustomers() {
        return customerRepository.getCustomers();
    }

    @PostMapping(path = "/bulk/customers")
    public List<Customer> bulkCreateCustomers(@RequestBody @Valid @Size(min = 1, max = 3, message = "should be of maximum 20 size")
                                              List<Customer> customers) {
        return customerRepository.createCustomers(customers);
    }

    @PostMapping(path = "/batch/customers")
    public ResponseEntity<String> batchUpdateCustomers(@RequestBody @Valid @Size(min = 1, max = 3, message = "should be of maximum 20 size")
                                                       List<CustomerBatchRequest> customerBatchRequests) {
        List<Customer> customerProcessed = new ArrayList<>();

        customerBatchRequests.forEach(customerBatchRequest -> {
            switch (customerBatchRequest.getBatchMethodType()) {
                case POST:
                    customerProcessed.addAll(customerRepository.createCustomers(customerBatchRequest.getCustomerData()));
                    break;
                case PUT:
                    customerProcessed.addAll(customerRepository.updateCustomers(customerBatchRequest.getCustomerData()));
                    break;
                case DELETE:
                    customerProcessed.addAll(customerRepository.deleteCustomers(customerBatchRequest.getCustomerData()));
                    break;
                default:
                    break;
            }
        });

        return ResponseEntity.ok("Customer Batch Request is processed successfully of customer size " + customerProcessed.size());
    }
}

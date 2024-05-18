package com.baeldung.api.bulkandbatch.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(@Autowired CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping(path = "/customers")
    public Set<Customer> customerBatchUpdate() {
        return customerRepository.getCustomers();
    }

    @PostMapping(path = "/customer")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerRepository.createCustomer(customer);
    }

    @PutMapping(path = "/customer")
    public Customer updateCustomer(@RequestBody Customer customer) {
        return customerRepository.updateCustomer(customer);
    }

    @DeleteMapping(path = "/customer")
    public String deleteCustomer(@RequestBody Customer customer) {
        customerRepository.deleteCustomer(customer);
        return "Customer deleted successfully";
    }

    @PostMapping(path = "/bulk/customers")
    public List<Customer> createCustomers(@RequestBody List<Customer> customers) {
        return customerRepository.createCustomers(customers);
    }

    @PostMapping(path = "/batch/customers")
    public ResponseEntity<String> batchUpdateCustomers(@RequestBody List<CustomerBatchRequest> customerBatchRequests) {
        List<Customer> customerProcessed = new ArrayList<>();

        customerBatchRequests.forEach(customerBatchRequest -> {
            switch (customerBatchRequest.getHttpMethodType()) {
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

        return ResponseEntity.ok("Customer Batch Processed Successfully for customer size " + customerProcessed.size());
    }
}

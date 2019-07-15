package com.example.application;

import com.example.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerCreator implements ICustomerCreator {
    private final ICustomerRepository customerRepository;

    public CustomerId save(CustomerRequest request) {
        Long id = customerRepository.generateId();
        Customer customer = new Customer(new CustomerId(id), request.getFirstName(), request.getLastName(), request.getAddress(), request.getEmail(), request.getPhone());
        return customerRepository.save(customer);
    }

}


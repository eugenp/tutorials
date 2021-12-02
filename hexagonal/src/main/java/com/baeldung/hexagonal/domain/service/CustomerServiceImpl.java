package com.baeldung.hexagonal.domain.service;


import com.baeldung.hexagonal.domain.model.Customer;
import com.baeldung.hexagonal.port.in.CustomerService;
import com.baeldung.hexagonal.port.out.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class CustomerServiceImpl
        implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer register(Customer customer) {
        // additional business logic
        return customerRepository.save(customer);
    }

    @Override
    public Customer getByUsername(String username) {
        // additional business logic
        return customerRepository.getByUsername(username);
    }
}


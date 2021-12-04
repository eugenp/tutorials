package com.baeldung.hexagonal.adapter.persistence;

import com.baeldung.hexagonal.adapter.persistence.repositories.InMemoryCustomerPersistencePort;
import com.baeldung.hexagonal.domain.model.Customer;
import com.baeldung.hexagonal.port.out.CustomerPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class InMemoryCustomerPersistenceAdapter implements CustomerPersistencePort {
    private InMemoryCustomerPersistencePort inMemoryCustomerRepository;

    @Override
    public Customer save(Customer customer) {
        // perform here mappings if needed
        return inMemoryCustomerRepository.save(customer);
    }

    @Override
    public Customer getByUsername(String username) {
        Customer customer = inMemoryCustomerRepository.getByUsername(username);
        // perform here mappings if needed
        return customer;
    }
}

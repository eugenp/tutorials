package com.baeldung.hexagonal.adapter.persistence;

import com.baeldung.hexagonal.adapter.persistence.mapper.JpaEntityMapper;
import com.baeldung.hexagonal.adapter.persistence.repositories.JpaCustomerRepository;
import com.baeldung.hexagonal.domain.model.Customer;
import com.baeldung.hexagonal.port.out.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Primary
@Component
public class JpaCustomerPersistenceAdapter implements CustomerRepository {

    private final JpaCustomerRepository jpaCustomerRepository;
    private final JpaEntityMapper jpaEnityMapper;

    @Override
    public Customer save(Customer customer) {
        return jpaEnityMapper.fromEntity(jpaCustomerRepository.save(jpaEnityMapper.toEntity(customer)));
    }

    @Override
    public Customer getByUsername(String username) {
        return jpaCustomerRepository.findByUsername(username)
                .map(customerEntity -> jpaEnityMapper.fromEntity(customerEntity))
                .orElseThrow(() -> new IllegalArgumentException("Invalid username: " + username));
    }
}

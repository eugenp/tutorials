package com.baeldung.hexagonal.adapter.persistence.mapper;

import com.baeldung.hexagonal.adapter.persistence.model.CustomerEntity;
import com.baeldung.hexagonal.domain.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JpaEntityMapper {
    public CustomerEntity toEntity(Customer customer) {
        return CustomerEntity.builder()
                .deposit(customer.getDeposit())
                .username(customer.getUsername())
                .password(customer.getPassword())
                .build();
    }

    public Customer fromEntity(CustomerEntity customerEntity) {
        return Customer.builder()
                .id(customerEntity.getId())
                .username(customerEntity.getUsername())
                .password(customerEntity.getPassword())
                .deposit(customerEntity.getDeposit())
                .build();
    }
}

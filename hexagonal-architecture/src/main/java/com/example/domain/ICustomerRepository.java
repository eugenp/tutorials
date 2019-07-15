package com.example.domain;

public interface ICustomerRepository {
    Long generateId();

    CustomerId save(Customer customer);
}

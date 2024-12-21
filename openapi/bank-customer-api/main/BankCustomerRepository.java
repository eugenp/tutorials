package com.example.bankcustomerapi.repository;

import com.example.bankcustomerapi.model.BankCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankCustomerRepository extends JpaRepository<BankCustomer, Long> {
}

package com.baeldung.loadtesting.repository;

import com.baeldung.loadtesting.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findByCustomerRewardsId(Integer rewardsId);
}

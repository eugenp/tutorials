package com.baeldung.boot.architecture.hexagonal.repository;

import com.baeldung.boot.architecture.hexagonal.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository  extends JpaRepository<Transaction, Integer> {
}

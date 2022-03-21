package com.baeldung.hexagonal.architecture.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.baeldung.hexagonal.architecture.example.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	@Query("select transaction from Transaction transaction where transaction.sourceAccount.id = :accountId or transaction.targetAccount.id = :accountId order by transaction.time desc")
	List<Transaction> findAllForAccount(@Param("accountId") Long accountId);

}

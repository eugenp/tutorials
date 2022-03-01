package com.baeldung.readonlytransactions.mysql.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.baeldung.readonlytransactions.mysql.spring.ReaderDS;
import com.baeldung.readonlytransactions.mysql.spring.entities.TransactionEntity;

import javax.transaction.Transactional;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    @ReaderDS
    @Query("Select 1 from TransactionEntity t where t.id = ?1")
    Long get(Long id);

    @Transactional
    default TransactionEntity persist(TransactionEntity transaction) {
        return this.save(transaction);
    }
}

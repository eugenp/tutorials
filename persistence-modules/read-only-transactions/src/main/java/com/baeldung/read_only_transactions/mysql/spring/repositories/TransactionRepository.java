package com.baeldung.read_only_transactions.mysql.spring.repositories;

import com.baeldung.read_only_transactions.mysql.spring.ReaderDS;
import com.baeldung.read_only_transactions.mysql.spring.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    @ReaderDS
    @Query("Select 1 from TransactionEntity t where t.id = ?1") Integer get(Long id);

    @Transactional
    default TransactionEntity persist(TransactionEntity transaction)  {
        return this.save(transaction);
    }
}

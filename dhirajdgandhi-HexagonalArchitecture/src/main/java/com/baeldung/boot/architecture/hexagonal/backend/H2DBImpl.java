package com.baeldung.boot.architecture.hexagonal.backend;

import com.baeldung.boot.architecture.hexagonal.entity.Transaction;
import com.baeldung.boot.architecture.hexagonal.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(
        value = "db.h2",
        havingValue = "true"
)
@Component
public class H2DBImpl implements IDBPort {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public int addTransaction(Transaction transaction) {
        int transactionId = transaction.getId();
        Transaction savedTransaction = transactionRepository.saveAndFlush(transaction);
        return savedTransaction.getId();
    }

    @Override
    public Transaction getTransaction(int transactionId) {
        try {
            return transactionRepository.getOne(transactionId);
        } catch(Exception e) {
            return null;
        }
    }
}

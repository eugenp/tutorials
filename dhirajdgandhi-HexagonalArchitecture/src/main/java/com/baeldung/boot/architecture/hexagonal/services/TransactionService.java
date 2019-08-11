package com.baeldung.boot.architecture.hexagonal.services;

import com.baeldung.boot.architecture.hexagonal.backend.IDBPort;
import com.baeldung.boot.architecture.hexagonal.entity.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TransactionService {
    private static final Logger LOG = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    IDBPort idbPort;

    public int addTransaction(Transaction transaction){
        LOG.info("Adding transaction : {}",transaction);
        int transactionId = idbPort.addTransaction(transaction);
        LOG.info("Transaction has been saved with transactionId : {}", transactionId);

        return transactionId;
    }

    public Transaction viewTransaction(int transactionId){
        LOG.info("Sending DB request to fetch transaction with ID : {}",transactionId);
        Transaction transaction = idbPort.getTransaction(transactionId);
        LOG.info("Retrieved transaction is : {}",transaction);
        return transaction;
    }
}

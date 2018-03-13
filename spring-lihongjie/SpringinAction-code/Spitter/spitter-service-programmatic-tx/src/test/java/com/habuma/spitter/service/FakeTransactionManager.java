package com.habuma.spitter.service;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

public class FakeTransactionManager 
    implements PlatformTransactionManager {

  public void commit(TransactionStatus txStatus)
          throws TransactionException {
  }

  public TransactionStatus getTransaction(
          TransactionDefinition txDefinition)
          throws TransactionException {
    return null;
  }

  public void rollback(TransactionStatus txStatus)
          throws TransactionException {
  }
}

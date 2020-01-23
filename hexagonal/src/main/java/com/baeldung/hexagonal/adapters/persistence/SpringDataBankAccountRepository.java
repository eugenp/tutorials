package com.baeldung.hexagonal.adapters.persistence;

import com.baeldung.hexagonal.application.domain.BankAccount;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataBankAccountRepository extends MongoRepository<BankAccount, Long> { }

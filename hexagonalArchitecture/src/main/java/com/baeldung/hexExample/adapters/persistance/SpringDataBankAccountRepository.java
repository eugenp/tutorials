package com.baeldung.hexExample.adapters.persistance;

import com.baeldung.hexExample.application.domain.BankAccount;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataBankAccountRepository extends MongoRepository<BankAccount, Long> { }
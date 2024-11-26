package com.baeldung.multipledb.repository.secondary;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.multipledb.model.Account;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {

    Account findByAccountDomain(String account);
}
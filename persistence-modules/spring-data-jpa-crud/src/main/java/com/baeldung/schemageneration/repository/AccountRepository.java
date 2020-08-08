package com.baeldung.schemageneration.repository;

import com.baeldung.schemageneration.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
    Account findByName(String name);
}

package com.baeldung.jpa.schemageneration.repository;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.jpa.schemageneration.model.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {
    Account findByName(String name);
}

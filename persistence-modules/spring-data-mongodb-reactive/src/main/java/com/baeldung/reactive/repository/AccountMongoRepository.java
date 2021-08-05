package com.baeldung.reactive.repository;

import com.baeldung.reactive.model.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AccountMongoRepository extends ReactiveMongoRepository<Account, String> {
}

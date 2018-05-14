package com.baeldung.spring.webflux;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends ReactiveMongoRepository<Contact, String> {

}

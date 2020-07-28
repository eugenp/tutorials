package com.baeldung.architecture.hexagonal.repositories.mondodb;

import java.util.UUID;

import com.baeldung.architecture.hexagonal.domain.models.Quote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataMongoQuoteRepository extends MongoRepository<Quote, UUID> {
}

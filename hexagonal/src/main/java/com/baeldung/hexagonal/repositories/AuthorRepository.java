package com.baeldung.hexagonal.repositories;

import com.baeldung.hexagonal.entities.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthorRepository extends MongoRepository<Author, Long> {
}

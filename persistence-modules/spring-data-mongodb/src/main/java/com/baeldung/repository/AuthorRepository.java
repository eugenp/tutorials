package com.baeldung.repository;

import com.baeldung.model.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface AuthorRepository extends MongoRepository<Author, UUID> {
}
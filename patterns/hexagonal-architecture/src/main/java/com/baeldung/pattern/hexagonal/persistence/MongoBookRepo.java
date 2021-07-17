package com.baeldung.pattern.hexagonal.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.pattern.hexagonal.domain.model.Book;

@Repository
public interface MongoBookRepo extends MongoRepository<Book, String> {

}
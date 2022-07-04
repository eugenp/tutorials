package com.baeldung.uuid.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.baeldung.uuid.model.Book;

public interface BookRepository extends MongoRepository<Book, UUID> {

}

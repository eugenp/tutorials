package com.baeldung.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.baeldung.model.Book;

public interface BookRepository extends MongoRepository<Book, UUID> {

}

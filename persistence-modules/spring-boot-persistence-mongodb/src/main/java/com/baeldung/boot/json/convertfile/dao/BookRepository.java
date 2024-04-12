package com.baeldung.boot.json.convertfile.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.baeldung.boot.json.convertfile.data.Book;

public interface BookRepository extends MongoRepository<Book, String> {

}

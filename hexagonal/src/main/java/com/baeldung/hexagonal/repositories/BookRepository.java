package com.baeldung.hexagonal.repositories;

import com.baeldung.hexagonal.entities.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, Long>{
}

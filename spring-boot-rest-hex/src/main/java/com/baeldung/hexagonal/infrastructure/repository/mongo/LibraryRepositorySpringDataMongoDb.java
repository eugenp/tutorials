package com.baeldung.hexagonal.infrastructure.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.hexagonal.domain.Book;

@Repository
public interface LibraryRepositorySpringDataMongoDb extends 
			MongoRepository<Book, String> {
	
}
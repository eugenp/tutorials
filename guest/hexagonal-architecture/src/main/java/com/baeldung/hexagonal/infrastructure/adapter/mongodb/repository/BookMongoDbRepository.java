package com.baeldung.hexagonal.infrastructure.adapter.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.hexagonal.infrastructure.adapter.mongodb.entity.BookMongoDbEntity;


@Repository
public interface BookMongoDbRepository extends MongoRepository<BookMongoDbEntity, Integer> {
	BookMongoDbEntity findByBookId(Integer bookId);
}

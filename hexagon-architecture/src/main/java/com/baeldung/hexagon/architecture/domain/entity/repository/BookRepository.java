package com.baeldung.hexagon.architecture.domain.entity.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.hexagon.architecture.domain.entity.Book;

@Repository
public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {

}
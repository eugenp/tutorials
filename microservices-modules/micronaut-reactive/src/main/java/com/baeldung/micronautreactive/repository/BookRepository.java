package com.baeldung.micronautreactive.repository;

import com.baeldung.micronautreactive.entity.Book;
import io.micronaut.context.annotation.Parameter;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.data.mongodb.annotation.MongoFindQuery;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.reactive.ReactorCrudRepository;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;

@MongoRepository
public interface BookRepository extends ReactorCrudRepository<Book, ObjectId> {

    @MongoFindQuery("{year: {$gt: :year}}")
    Flux<Book> findByYearGreaterThan(int year);
}

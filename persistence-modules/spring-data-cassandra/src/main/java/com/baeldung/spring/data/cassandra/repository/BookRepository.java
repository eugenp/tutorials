package com.baeldung.spring.data.cassandra.repository;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.spring.data.cassandra.model.Book;

@Repository
public interface BookRepository extends CassandraRepository<Book, UUID> {

    Iterable<Book> findByTitleAndPublisher(String title, String publisher);

}

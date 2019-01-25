package com.baeldung.hexagonal.adapter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.hexagonal.domain.Book;
import com.baeldung.hexagonal.repository.BookRepository;

@Repository
public interface InMemoryBookRepository extends BookRepository, CrudRepository<Book, String> {

}

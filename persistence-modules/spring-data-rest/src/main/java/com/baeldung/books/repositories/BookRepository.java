package com.baeldung.books.repositories;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.books.models.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
}

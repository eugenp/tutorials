package com.baeldung.repositories;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.models.Book;

public interface BookRepository extends CrudRepository<Book, Long> {

}

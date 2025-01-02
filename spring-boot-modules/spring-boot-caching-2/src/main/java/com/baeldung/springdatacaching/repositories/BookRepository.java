package com.baeldung.springdatacaching.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import com.baeldung.springdatacaching.model.Book;

public interface BookRepository extends CrudRepository<Book, UUID> {

    @Cacheable(value = "books", unless = "#a0=='Foundation'")
    Optional<Book> findFirstByTitle(String title);

}

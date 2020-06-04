package com.baeldung.springdatacaching.repositories;

import com.baeldung.springdatacaching.model.Book;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends CrudRepository<Book, UUID> {

    @Cacheable(value = "books", unless="#a0=='Foundation'")
    Optional<Book> findFirstByTitle(String title);

}

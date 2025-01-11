package com.baeldung.books.repositories;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.books.models.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {

}

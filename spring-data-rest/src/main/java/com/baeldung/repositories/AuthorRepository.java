package com.baeldung.repositories;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.models.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {

}

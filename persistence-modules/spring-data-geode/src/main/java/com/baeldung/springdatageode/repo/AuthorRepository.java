package com.baeldung.springdatageode.repo;

import com.baeldung.springdatageode.domain.Author;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {

}

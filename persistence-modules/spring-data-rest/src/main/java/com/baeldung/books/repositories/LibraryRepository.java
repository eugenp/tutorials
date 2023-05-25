package com.baeldung.books.repositories;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.books.models.Library;

public interface LibraryRepository extends CrudRepository<Library, Long> {

}

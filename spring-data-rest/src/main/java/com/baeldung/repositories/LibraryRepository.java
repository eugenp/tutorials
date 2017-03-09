package com.baeldung.repositories;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.models.Library;

public interface LibraryRepository extends CrudRepository<Library, Long> {

}

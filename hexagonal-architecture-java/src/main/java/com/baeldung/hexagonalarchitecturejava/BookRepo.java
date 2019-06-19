package com.baeldung.hexagonalarchitecturejava;

import org.springframework.data.repository.CrudRepository;

public interface BookRepo extends CrudRepository<Book, Long> {
}

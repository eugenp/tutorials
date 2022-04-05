package com.baeldung.boot.readonlyrepository;

import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends BookReadOnlyRepository, CrudRepository<Book, Long> {
}

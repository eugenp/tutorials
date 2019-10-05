package com.baeldung.hexagonal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {
    BookEntity findByName(String name);
}

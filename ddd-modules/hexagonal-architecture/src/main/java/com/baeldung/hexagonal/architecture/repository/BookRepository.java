package com.baeldung.hexagonal.architecture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByNameAndShelfNo(String name, Integer shelfNo);
}

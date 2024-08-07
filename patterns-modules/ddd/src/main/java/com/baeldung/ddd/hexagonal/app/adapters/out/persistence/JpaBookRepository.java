package com.baeldung.ddd.hexagonal.app.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.ddd.hexagonal.app.domain.Book;

public interface JpaBookRepository extends JpaRepository<Book, Long> {
}

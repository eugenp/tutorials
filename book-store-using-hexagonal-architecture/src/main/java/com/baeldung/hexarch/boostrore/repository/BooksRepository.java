package com.baeldung.hexarch.boostrore.repository;

import com.baeldung.hexarch.boostrore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(final String isbn);
}

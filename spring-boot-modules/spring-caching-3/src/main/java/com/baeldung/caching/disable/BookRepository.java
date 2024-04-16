package com.baeldung.caching.disable;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookReview, Long> {

    List<BookReview> findByIsbn(String isbn);
}

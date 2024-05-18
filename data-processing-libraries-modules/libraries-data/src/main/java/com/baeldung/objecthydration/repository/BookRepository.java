package com.baeldung.objecthydration.repository;

import com.baeldung.objecthydration.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}

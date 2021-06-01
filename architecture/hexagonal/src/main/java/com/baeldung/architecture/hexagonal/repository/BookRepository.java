package com.baeldung.architecture.hexagonal.repository;

import com.baeldung.architecture.hexagonal.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository public interface BookRepository extends JpaRepository<Book, Long> {

        Optional<Book> findByIsbn(Long isbn);

        Optional<Book> findByTitle(String title);

}

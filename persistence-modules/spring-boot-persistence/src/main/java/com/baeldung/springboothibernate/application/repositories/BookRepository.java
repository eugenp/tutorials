package com.baeldung.springboothibernate.application.repositories;

import com.baeldung.springboothibernate.application.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}

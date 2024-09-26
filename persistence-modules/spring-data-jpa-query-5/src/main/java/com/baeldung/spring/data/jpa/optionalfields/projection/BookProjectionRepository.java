package com.baeldung.spring.data.jpa.optionalfields.projection;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.baeldung.spring.data.jpa.optionalfields.Book;

@Repository
public interface BookProjectionRepository extends JpaRepository<Book, Integer> {
    @Query(value = "SELECT b.id as id, b.title, b.author FROM Book b", nativeQuery = true)
    List<BookProjection> fetchBooks();
}

package com.baeldung.spring.data.jpa.optionalfields.dto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.baeldung.spring.data.jpa.optionalfields.Book;
import com.baeldung.spring.data.jpa.optionalfields.BookDto;

@Repository
public interface BookDtoRepository extends JpaRepository<Book, Integer> {
    @Query(value = "SELECT new com.baeldung.spring.data.jpa.optionalfields.BookDto(b.id, b.title, b.author) FROM Book b")
    List<BookDto> fetchBooks();
}

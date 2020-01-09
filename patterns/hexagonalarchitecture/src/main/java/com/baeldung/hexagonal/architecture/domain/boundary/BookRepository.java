package com.baeldung.hexagonal.architecture.domain.boundary;

import com.baeldung.hexagonal.architecture.domain.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository {
    String add(Book book);

    Optional<Book> findByIsbn(String isbn);

    List<Book> findByTitle(String title);

    List<Book> findByAuthor(String author);
}

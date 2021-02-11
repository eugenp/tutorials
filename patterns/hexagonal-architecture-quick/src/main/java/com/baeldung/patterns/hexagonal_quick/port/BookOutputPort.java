package com.baeldung.patterns.hexagonal_quick.port;

import java.util.Optional;

import com.baeldung.patterns.hexagonal_quick.domain.Book;

public interface BookOutputPort {
    Optional<Book> findBookByIsbn(String isbn);

    Book createBook(Book book);
}
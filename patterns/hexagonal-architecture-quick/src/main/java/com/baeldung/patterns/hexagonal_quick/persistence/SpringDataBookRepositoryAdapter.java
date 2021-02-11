package com.baeldung.patterns.hexagonal_quick.persistence;

import java.util.Optional;

import com.baeldung.patterns.hexagonal_quick.domain.Book;
import com.baeldung.patterns.hexagonal_quick.port.BookOutputPort;

public class SpringDataBookRepositoryAdapter implements BookOutputPort {

    @Override
    public Optional<Book> findBookByIsbn(String isbn) {
        return null;
    }

    @Override
    public Book createBook(Book book) {
        return null;
    }
}

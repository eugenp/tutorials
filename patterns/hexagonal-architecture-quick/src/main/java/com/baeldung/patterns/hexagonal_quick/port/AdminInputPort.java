package com.baeldung.patterns.hexagonal_quick.port;

import com.baeldung.patterns.hexagonal_quick.domain.Book;

public interface AdminInputPort {
    Book getBook(String isbn);

    Book addBook(Book book);
}
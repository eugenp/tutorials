package com.baeldung.patterns.hexagonal_quick.port;

import com.baeldung.patterns.hexagonal_quick.domain.Book;

public interface AdminInputPort {
    Book addBook(Book book);
}
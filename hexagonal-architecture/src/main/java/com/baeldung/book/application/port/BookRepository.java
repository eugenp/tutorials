package com.baeldung.book.application.port;

import com.baeldung.book.application.domain.Book;

public interface BookRepository {
    Book getByName(String name);
    void save(Book book);
}

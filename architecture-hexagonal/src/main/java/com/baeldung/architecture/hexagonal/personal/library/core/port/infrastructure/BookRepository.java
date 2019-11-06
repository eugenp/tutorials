package com.baeldung.architecture.hexagonal.personal.library.core.port.infrastructure;

import com.baeldung.architecture.hexagonal.personal.library.core.domain.Book;

import java.util.List;

public interface BookRepository {

    Book create(Book book);

    boolean remove(String isbn);

    List<Book> getAll();

    Book getBookByIsbn(String isbn);

    void removeAll();
}

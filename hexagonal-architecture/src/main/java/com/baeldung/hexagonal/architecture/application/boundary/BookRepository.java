package com.baeldung.hexagonal.architecture.application.boundary;

import com.baeldung.hexagonal.architecture.application.entity.Book;

public interface BookRepository {
    void save(Book book);
}

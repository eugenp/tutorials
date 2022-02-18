package com.baeldung.pattern.architecture.hexagonal.domain.repository;

import java.util.List;

import com.baeldung.pattern.architecture.hexagonal.domain.entity.Book;

public interface BookRepository {
    List<Book> getBooks();
}

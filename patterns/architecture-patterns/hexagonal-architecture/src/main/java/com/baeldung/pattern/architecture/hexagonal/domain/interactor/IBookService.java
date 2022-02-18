package com.baeldung.pattern.architecture.hexagonal.domain.interactor;

import java.util.List;

import com.baeldung.pattern.architecture.hexagonal.domain.entity.Book;

public interface IBookService {
    List<Book> getBooks();
}

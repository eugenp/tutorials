package com.baeldung.hexagonal.architecture.application.control;

import com.baeldung.hexagonal.architecture.application.boundary.BookRepository;
import com.baeldung.hexagonal.architecture.application.entity.Book;

import java.util.ArrayList;
import java.util.List;

public class MockBookRepository implements BookRepository {

    List<Book> mockDatabase = new ArrayList<>();

    @Override
    public void save(Book book) {
        mockDatabase.add(book);
    }
}

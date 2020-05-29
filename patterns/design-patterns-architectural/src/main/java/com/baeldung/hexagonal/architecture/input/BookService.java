package com.baeldung.hexagonal.architecture.input;

import com.baeldung.hexagonal.architecture.domain.Book;

import java.util.List;
import java.util.UUID;

public interface BookService {
    public UUID createBook(Book book);

    public void updateBook(Book book);

    public Book getBookById(UUID id);

    public List<Book> getAllBooks();
}
package com.baeldung.hexagonal.architecture.output;

import com.baeldung.hexagonal.architecture.domain.Book;

import java.util.List;
import java.util.UUID;

public interface BookRepository {
    public void saveBook(Book book);

    public Book getBookById(UUID id);

    public List<Book> getAllBooks();
}
package com.demo.services;

import com.demo.models.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookService {
    List<Book> getAllBooks();

    Optional<Book> getBookById(UUID uuid);

    UUID createBook(Book newBook);

    void deleteBookById(UUID id);
}

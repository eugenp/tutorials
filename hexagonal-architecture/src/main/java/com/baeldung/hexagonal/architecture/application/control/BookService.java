package com.baeldung.hexagonal.architecture.application.control;

import com.baeldung.hexagonal.architecture.application.boundary.BookRepository;
import com.baeldung.hexagonal.architecture.application.entity.Book;

import java.util.Objects;

@Service
public class BookService {

    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void registerBook(Book book) {
        Objects.requireNonNull(book, "Book can't be <null>");
        bookRepository.save(book);
    }
}

package com.baeldung.hexagonal.domain;

import com.baeldung.hexagonal.port.incoming.BookService;
import com.baeldung.hexagonal.port.outgoing.BookRepository;
import com.baeldung.hexagonal.port.outgoing.BookWriter;

import java.util.List;

public class BookServiceDomain implements BookService {
    private BookRepository repository;
    private BookWriter writer;

    public BookServiceDomain(BookRepository repository, BookWriter writer) {
        this.repository = repository;
        this.writer = writer;
    }

    @Override
    public void transformBookTitles() {
        List<Book> books = repository.getAllBooks();
        books.forEach(book -> {
            String title = book.getTitle();
            book.setTitle(title.toUpperCase());
        });
        writer.writeBooks(books);
    }
}

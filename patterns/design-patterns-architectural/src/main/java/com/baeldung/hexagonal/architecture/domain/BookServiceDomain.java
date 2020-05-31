package com.baeldung.hexagonal.architecture.domain;

import com.baeldung.hexagonal.architecture.port.incoming.BookService;
import com.baeldung.hexagonal.architecture.port.outgoing.BookRepository;
import com.baeldung.hexagonal.architecture.port.outgoing.BookWriter;

import java.util.List;

public class BookServiceDomain implements BookService {
    private BookRepository repository;
    private BookWriter writer;

    public BookServiceDomain(BookRepository repository, BookWriter writer) {
        this.repository = repository;
        this.writer = writer;
    }
    @Override
    public void invoke() {
        List<Book> books = repository.getAllBooks();
        books.forEach(book -> {
            String title = book.getTitle();
            book.setTitle(title.toUpperCase());
        });
        writer.writeBooks(books);
    }
}

package com.baeldung.hexagonal.architecture.domain;

import com.baeldung.hexagonal.architecture.domain.boundary.BookRepository;
import com.baeldung.hexagonal.architecture.domain.boundary.BookService;
import java.util.List;

public class DomainBookService implements BookService {
    private final BookRepository bookRepository;

    public DomainBookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public String add(Book book) {
        return this.bookRepository.add(book);
    }

    @Override
    public Book searchByIsbn(String isbn) {
        return this.bookRepository.findByIsbn(isbn)
            .orElseThrow(RuntimeException::new);
    }

    @Override
    public List<Book> searchByAuthor(String author) {
        return this.bookRepository.findByAuthor(author);
    }

    @Override
    public List<Book> searchByTitle(String title) {
        return this.bookRepository.findByTitle(title);
    }
}

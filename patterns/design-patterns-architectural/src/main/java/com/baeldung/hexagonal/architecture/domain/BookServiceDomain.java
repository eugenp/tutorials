package com.baeldung.hexagonal.architecture.domain;

import com.baeldung.hexagonal.architecture.input.BookService;
import com.baeldung.hexagonal.architecture.output.BookRepository;

import java.util.List;
import java.util.UUID;

public class BookServiceDomain implements BookService {

    private BookRepository repository;

    public BookServiceDomain(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public UUID createBook(Book book) {
        repository.saveBook(book);
        return book.getId();
    }

    @Override
    public void updateBook(Book book) {
        repository.saveBook(book);
    }

    @Override
    public Book getBookById(UUID id) {
        return repository.getBookById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return repository.getAllBooks();
    }
}

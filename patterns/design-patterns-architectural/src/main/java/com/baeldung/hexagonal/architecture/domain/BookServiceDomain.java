package com.baeldung.hexagonal.architecture.domain;

import com.baeldung.hexagonal.architecture.input.BookService;
import com.baeldung.hexagonal.architecture.output.BookRepository;

import java.util.List;

public class BookServiceDomain implements BookService {

    private BookRepository repository;

    public BookServiceDomain(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public String createBook(Book book) {
        repository.saveBook(book);
        return book.getId();
    }

    @Override
    public void updateBook(Book book) {
        repository.updateBook(book);
    }

    @Override
    public Book getBookById(String id) {
        return repository.getBookById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return repository.getAllBooks();
    }

    @Override
    public void deleteBook(String id) {
        repository.deleteBook(id);
    }
}

package com.baeldung.hexagonal.domain.business;

import com.baeldung.hexagonal.domain.models.Book;
import com.baeldung.hexagonal.domain.ports.BookRepository;
import com.baeldung.hexagonal.domain.ports.BookService;
import com.baeldung.hexagonal.domain.ports.BookValidator;

import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private BookValidator bookValidator;

    public BookServiceImpl(BookRepository bookRepository, BookValidator bookValidator) {
        this.bookRepository = bookRepository;
        this.bookValidator = bookValidator;
    }

    @Override
    public int addBook(Book book) {
        if (!bookValidator.validate(book)) {
            return -1;
        }
        return bookRepository.storeBook(book);
    }

    @Override
    public Optional<Book> getBook(int id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}

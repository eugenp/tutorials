package com.baeldung.architecture.hexagonal.personal.library.application.adapter.business.service;

import com.baeldung.architecture.hexagonal.personal.library.core.domain.Book;
import com.baeldung.architecture.hexagonal.personal.library.core.port.business.service.AddBookService;
import com.baeldung.architecture.hexagonal.personal.library.core.port.infrastructure.BookRepository;

public class AddBookServiceImpl implements AddBookService {

    private final BookRepository bookRepository;

    public AddBookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book addBook(Book book) {
        return bookRepository.create(book);
    }

}

package com.baeldung.architecture.hexagonal.personal.library.application.adapter.business.service;

import com.baeldung.architecture.hexagonal.personal.library.core.port.business.service.RemoveBookService;
import com.baeldung.architecture.hexagonal.personal.library.core.port.infrastructure.BookRepository;

public class RemoveBookServiceImpl implements RemoveBookService {

    private final BookRepository bookRepository;

    public RemoveBookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean removeBook(String isbn) {
        return bookRepository.remove(isbn);
    }

    @Override
    public void removeAllBooks() {
        bookRepository.removeAll();
    }

}

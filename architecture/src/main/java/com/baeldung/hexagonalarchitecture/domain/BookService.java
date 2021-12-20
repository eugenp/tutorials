package com.baeldung.hexagonalarchitecture.domain;

import java.util.List;

public class BookService implements IBookService {

    private IBookRepository bookRepository;

    public BookService(IBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> listAllBooks() {
        return bookRepository.findAllBooks();
    }
}
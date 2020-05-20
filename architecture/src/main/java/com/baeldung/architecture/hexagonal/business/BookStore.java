package com.baeldung.architecture.hexagonal.business;

import java.util.List;

import com.baeldung.architecture.hexagonal.business.bordery.driven.BookPersistencePort;
import com.baeldung.architecture.hexagonal.business.bordery.driver.BookRentalPort;
import com.baeldung.architecture.hexagonal.business.bordery.dto.BookDto;

public class BookStore implements BookRentalPort {

    private final BookPersistencePort bookPersistence;

    public BookStore(BookPersistencePort bookPersistence) {
        this.bookPersistence = bookPersistence;
    }

    @Override
    public List<BookDto> searchByName(String partOfTheName) {
        return bookPersistence.findByNameContaining(partOfTheName);
    }

    @Override
    public BookDto donate(BookDto book) {
        return bookPersistence.save(book);
    }

}

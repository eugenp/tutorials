package com.baeldung.hexagonal.bookstore.core;

import com.baeldung.hexagonal.bookstore.entity.Book;
import com.baeldung.hexagonal.bookstore.secondaryport.BookStoreRepository;

public class BookStoreImpl implements BookStore {

    BookStoreRepository bookStoreRepository;

    public BookStoreImpl(BookStoreRepository bookStoreRepository) {
        super();
        this.bookStoreRepository = bookStoreRepository;
    }

    @Override
    public Book getBook(String isbnNumber) {
        if (isValidISBN(isbnNumber))
            return bookStoreRepository.findBookByIsbn(isbnNumber);
        else
            return null;
    }

    private boolean isValidISBN(String isbnNumber) {
        if (isbnNumber.length() == 13)
            return true;
        return false;
    }
}

package com.baeldung.hexagonal.bookstore.primaryport;

import com.baeldung.hexagonal.bookstore.core.BookStore;
import com.baeldung.hexagonal.bookstore.model.Book;

public class BookFinderMockAdaptor implements BookFinder {

    BookStore bookStore;

    public BookFinderMockAdaptor(BookStore bookStore) {
        super();
        this.bookStore = bookStore;
    }

    @Override
    public Book findBook(String isbnNumber) {
        return bookStore.getBook(isbnNumber);
    }

}

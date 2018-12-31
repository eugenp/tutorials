package com.baeldung.hexagonal.bookstore.primaryport;

import com.baeldung.hexagonal.bookstore.core.BookStore;
import com.baeldung.hexagonal.bookstore.entity.Book;

public class ConsoleBookFinderAdaptor implements BookFinder {

    BookStore bookStore;

    public ConsoleBookFinderAdaptor(BookStore bookStore) {
        super();
        this.bookStore = bookStore;
    }

    @Override
    public Book findBook(String isbnNumber) {

        return bookStore.getBook(isbnNumber);
    }

}

package com.baeldung.hexagonal.bookfinder.adapter;

import com.baeldung.hexagonal.bookfinder.domain.Book;
import com.baeldung.hexagonal.bookfinder.port.BookLocator;

public class BookLocatorMock implements BookLocator {

    private Book book;

    public BookLocatorMock() {
        book = new Book("Introduction to Java", "James Gosling", "ISB0000258");
        book.setContent("Java is an object oriented programming language....");
    }

    @Override
    public Book findBookWithPassage(String passage) {
        if (book.getContent()
            .toLowerCase()
            .contains(passage.toLowerCase())) {
            return book;
        }
        return null;
    }

}

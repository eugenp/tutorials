package com.baeldung.ditypes;

import org.springframework.stereotype.Service;

@Service("bookServiceOtherImpl")
public class BookServiceOtherImpl implements BookService {
    public Boolean create(Book book) {
        System.out.println("Saving: " + book.toString());
        return true;
    }

    public Book read(String isbn) {
        System.out.println("Reading: " + isbn);
        return new Book("Title", "1234SDK");
    }

    public Boolean update(String isbn, Book book) {
        System.out.println("Updating " + isbn + " with " + book.toString());
        return true;
    }

    public Boolean delete(String isbn) {
        System.out.println("Deleting " + isbn);
        return true;
    }
}

package com.baeldung.patterns.hexagonal_quick.exception;

public class BookNotFoundException extends LibraryException {

    public BookNotFoundException(String isbn) {
        super("Book not found with isbn " + isbn);
    }
}

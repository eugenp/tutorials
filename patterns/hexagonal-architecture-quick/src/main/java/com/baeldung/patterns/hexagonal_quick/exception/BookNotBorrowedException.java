package com.baeldung.patterns.hexagonal_quick.exception;

public class BookNotBorrowedException extends LibraryException {

    public BookNotBorrowedException(String borrowId, String isbn) {
        super(String.format("Book with isbn %s not found under borrow id %s", isbn, borrowId));
    }
}

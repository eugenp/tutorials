package com.baeldung.patterns.hexagonal_quick.exception;

public class BorrowRecordNotFoundException extends LibraryException {

    public BorrowRecordNotFoundException(String borrowId) {
        super("Borrow record not found with id " + borrowId);
    }
}

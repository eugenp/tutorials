package com.baeldung.patterns.hexagonal_quick.persistence;

import java.util.Optional;

import com.baeldung.patterns.hexagonal_quick.domain.Book;
import com.baeldung.patterns.hexagonal_quick.domain.BorrowRecord;
import com.baeldung.patterns.hexagonal_quick.domain.BorrowedBook;
import com.baeldung.patterns.hexagonal_quick.port.BorrowOutputPort;

public class SpringDataBorrowRepositoryAdapter implements BorrowOutputPort {

    @Override
    public Optional<BorrowRecord> findBorrowForBorrowId(String borrowId) {
        return Optional.empty();
    }

    @Override
    public Optional<BorrowRecord> findBorrowForUser(String username) {
        return Optional.empty();
    }

    @Override
    public BorrowRecord createNewBorrow(String username, BorrowedBook book) {
        return null;
    }

    @Override
    public BorrowRecord addForExistingBorrow(String borrowId, BorrowedBook book) {
        return null;
    }

    @Override
    public void removeBookFromBorrow(String borrowId, Book book) {

    }
}

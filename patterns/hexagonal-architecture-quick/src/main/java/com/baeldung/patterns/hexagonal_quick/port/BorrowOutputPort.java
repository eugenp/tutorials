package com.baeldung.patterns.hexagonal_quick.port;

import java.util.Optional;

import com.baeldung.patterns.hexagonal_quick.domain.Book;
import com.baeldung.patterns.hexagonal_quick.domain.BorrowRecord;
import com.baeldung.patterns.hexagonal_quick.domain.BorrowedBook;

public interface BorrowOutputPort {
    Optional<BorrowRecord> findBorrowForBorrowId(String borrowId);

    Optional<BorrowRecord> findBorrowForUser(String username);

    BorrowRecord createNewBorrow(String username, BorrowedBook book);

    BorrowRecord addForExistingBorrow(String borrowId, BorrowedBook book);

    void removeBookFromBorrow(String borrowId, Book book);
}
package com.baeldung.patterns.hexagonal_quick.persistence;

import java.util.*;

import org.springframework.stereotype.Component;

import com.baeldung.patterns.hexagonal_quick.domain.Book;
import com.baeldung.patterns.hexagonal_quick.domain.BorrowRecord;
import com.baeldung.patterns.hexagonal_quick.domain.BorrowedBook;
import com.baeldung.patterns.hexagonal_quick.persistence.model.BorrowRecordData;
import com.baeldung.patterns.hexagonal_quick.persistence.model.BorrowedBookData;
import com.baeldung.patterns.hexagonal_quick.port.BorrowOutputPort;
import com.baeldung.patterns.hexagonal_quick.util.Converter;

@Component
public class InMemoryBorrowRepositoryAdapter implements BorrowOutputPort {

    private final Map<String, BorrowRecordData> borrowRecords;
    private final Converter<BorrowRecordData, BorrowRecord> borrowRecordConverter;

    public InMemoryBorrowRepositoryAdapter(Converter<BorrowRecordData, BorrowRecord> borrowRecordConverter) {
        this.borrowRecordConverter = borrowRecordConverter;
        this.borrowRecords = new HashMap<>();
    }

    @Override
    public Optional<BorrowRecord> findBorrowForBorrowId(String borrowId) {
        final BorrowRecordData borrowRecordData = borrowRecords.get(borrowId);
        if (borrowRecordData == null) {
            return Optional.empty();
        }
        return Optional.of(borrowRecordConverter.convert(borrowRecordData));
    }

    @Override
    public Optional<BorrowRecord> findBorrowForUser(String username) {
        BorrowRecordData borrowRecordData = null;
        for (BorrowRecordData borrowRecord : borrowRecords.values()) {
            if (username.equals(borrowRecord.getUsername())) {
                borrowRecordData = borrowRecord;
                break;
            }
        }

        if (borrowRecordData == null) {
            return Optional.empty();
        }

        return Optional.of(borrowRecordConverter.convert(borrowRecordData));
    }

    @Override
    public BorrowRecord createNewBorrow(String username, BorrowedBook borrowedBook) {
        String borrowId = String.format("%d_%s", System.currentTimeMillis(), username);
        BorrowRecordData borrowRecordData = BorrowRecordData.createForOneBorrow(borrowId, username, borrowedBook);
        borrowRecords.put(borrowId, borrowRecordData);
        return borrowRecordConverter.convert(borrowRecordData);
    }

    @Override
    public BorrowRecord addForExistingBorrow(String borrowId, BorrowedBook borrowedBook) {
        final BorrowRecordData borrowRecordData = borrowRecords.get(borrowId);

        Collection<BorrowedBookData> updatedBorrows = new ArrayList<>(borrowRecordData.getBorrowedBooks());
        updatedBorrows.add(BorrowedBookData.createFrom(borrowedBook));

        borrowRecordData.setBorrowedBooks(updatedBorrows);
        return borrowRecordConverter.convert(borrowRecordData);
    }

    @Override
    public void removeBookFromBorrow(String borrowId, Book book) {
        final BorrowRecordData borrowRecordData = borrowRecords.get(borrowId);

        Collection<BorrowedBookData> updatedBorrows = new ArrayList<>(borrowRecordData.getBorrowedBooks());
        for (BorrowedBookData borrowedBook : borrowRecordData.getBorrowedBooks()) {
            if (book.getIsbn().equals(borrowedBook.getBook().getIsbn())) {
                updatedBorrows.remove(borrowedBook);
            }
        }

        if (updatedBorrows.isEmpty()) {
            borrowRecords.remove(borrowId);
        } else {
            borrowRecordData.setBorrowedBooks(updatedBorrows);
        }
    }
}
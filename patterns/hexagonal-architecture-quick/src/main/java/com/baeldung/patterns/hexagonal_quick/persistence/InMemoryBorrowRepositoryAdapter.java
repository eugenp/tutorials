package com.baeldung.patterns.hexagonal_quick.persistence;

import java.util.*;

import com.baeldung.patterns.hexagonal_quick.domain.Book;
import com.baeldung.patterns.hexagonal_quick.domain.BorrowRecord;
import com.baeldung.patterns.hexagonal_quick.domain.BorrowedBook;
import com.baeldung.patterns.hexagonal_quick.persistence.model.BorrowRecordData;
import com.baeldung.patterns.hexagonal_quick.persistence.model.BorrowedBookData;
import com.baeldung.patterns.hexagonal_quick.port.BorrowOutputPort;
import com.baeldung.patterns.hexagonal_quick.util.Converter;

public class InMemoryBorrowRepositoryAdapter implements BorrowOutputPort {

    private final Map<String, BorrowRecordData> borrowRecords;
    private final Converter<BorrowRecordData, BorrowRecord> borrowRecordDataToBorrowRecordConverter;

    public InMemoryBorrowRepositoryAdapter
            (Map<String, BorrowRecordData> initialData, Converter<BorrowRecordData, BorrowRecord> borrowRecordConverter) {
        this.borrowRecordDataToBorrowRecordConverter = borrowRecordConverter;
        this.borrowRecords = new HashMap<>();
        this.borrowRecords.putAll(initialData);
    }

    @Override
    public Optional<BorrowRecord> findBorrowForBorrowId(String borrowId) {
        final BorrowRecordData borrowRecordData = borrowRecords.get(borrowId);
        if (borrowRecordData == null) {
            return Optional.empty();
        }
        return Optional.of(borrowRecordDataToBorrowRecordConverter.convert(borrowRecordData));
    }

    @Override
    public Optional<BorrowRecord> findBorrowForUser(String username) {
        String borrowId = null;
        for (String borrowKey : borrowRecords.keySet()) {
            if (borrowKey.endsWith(username)) {
                borrowId = borrowKey;
            }
        }

        if (borrowId == null) {
            return Optional.empty();
        }

        final BorrowRecordData borrowRecordData = borrowRecords.get(borrowId);
        return Optional.of(borrowRecordDataToBorrowRecordConverter.convert(borrowRecordData));
    }

    @Override
    public BorrowRecord createNewBorrow(String username, BorrowedBook borrowedBook) {
        String borrowId = String.format("%d_%s", System.currentTimeMillis(), username);
        final BorrowRecordData borrowRecordData = BorrowRecordData.createForOneBorrow(borrowId, borrowedBook);
        borrowRecords.put(borrowId, borrowRecordData);
        return borrowRecordDataToBorrowRecordConverter.convert(borrowRecordData);
    }

    @Override
    public BorrowRecord addForExistingBorrow(String borrowId, BorrowedBook borrowedBook) {
        final BorrowRecordData borrowRecordData = borrowRecords.get(borrowId);

        Collection<BorrowedBookData> updatedBorrows = new ArrayList<>(borrowRecordData.getBorrowedBooks());
        updatedBorrows.add(BorrowedBookData.createFrom(borrowedBook));

        borrowRecordData.setBorrowedBooks(updatedBorrows);
        return borrowRecordDataToBorrowRecordConverter.convert(borrowRecordData);
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
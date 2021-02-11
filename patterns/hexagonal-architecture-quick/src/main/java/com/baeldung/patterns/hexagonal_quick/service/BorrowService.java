package com.baeldung.patterns.hexagonal_quick.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.baeldung.patterns.hexagonal_quick.domain.Book;
import com.baeldung.patterns.hexagonal_quick.domain.BorrowRecord;
import com.baeldung.patterns.hexagonal_quick.domain.BorrowedBook;
import com.baeldung.patterns.hexagonal_quick.domain.ReturnRecord;
import com.baeldung.patterns.hexagonal_quick.exception.BookNotBorrowedException;
import com.baeldung.patterns.hexagonal_quick.exception.BookNotFoundException;
import com.baeldung.patterns.hexagonal_quick.exception.BorrowRecordNotFoundException;
import com.baeldung.patterns.hexagonal_quick.port.BookOutputPort;
import com.baeldung.patterns.hexagonal_quick.port.BorrowInputPort;
import com.baeldung.patterns.hexagonal_quick.port.BorrowOutputPort;

@Service
public class BorrowService implements BorrowInputPort {
    private final BookOutputPort bookOutputPort;
    private final BorrowOutputPort borrowOutputPort;

    public BorrowService(BookOutputPort bookOutputPort, BorrowOutputPort borrowOutputPort) {
        this.bookOutputPort = bookOutputPort;
        this.borrowOutputPort = borrowOutputPort;
    }

    @Override
    public BorrowRecord borrowBook(String isbn, String username) {
        Book book = bookOutputPort.findBookByIsbn(isbn)
            .orElseThrow(() -> new BookNotFoundException(isbn));

        Date dateInTenDays = Date.from(LocalDateTime.now().plusDays(10).toInstant(ZoneOffset.UTC));
        BorrowedBook borrowedBook = new BorrowedBook(book, dateInTenDays);
        return borrowOutputPort.findBorrowForUser(username)
            .map(existingRecord -> borrowOutputPort.addForExistingBorrow(existingRecord.getBorrowId(), borrowedBook))
            .orElseGet(() -> borrowOutputPort.createNewBorrow(username, borrowedBook));
    }

    @Override
    public ReturnRecord returnBook(String borrowId, String isbn) {
        BorrowRecord borrowRecord = borrowOutputPort.findBorrowForBorrowId(borrowId)
            .orElseThrow(() -> new BorrowRecordNotFoundException(borrowId));

        for (BorrowedBook borrowedBook : borrowRecord.getBorrowedBooks()) {
            if (isbn.equals(borrowedBook.getBook().getIsbn())) {
                borrowOutputPort.removeBookFromBorrow(borrowId, borrowedBook.getBook());
                return new ReturnRecord(borrowedBook.getBook(), new Random().nextInt(1000));
            }
        }
        throw new BookNotBorrowedException(borrowId, isbn);
    }
}

package com.baeldung.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.pojo.Book;
import com.baeldung.pojo.Borrower;
import com.baeldung.pojo.BorrowingRecord;
import com.baeldung.repo.BorrowingRecordRepository;

@Service
public class LibraryService {

    @Autowired
    private BorrowingRecordRepository recordRepo;

    public BorrowingRecord borrowBook(String borrowerUid, String bookNo) throws Exception {
        Borrower borrower = validateBorrower(borrowerUid);

        Book book = validateBook(bookNo);

        BorrowingRecord record = new BorrowingRecord();

        LocalDate borrowDate = LocalDate.now();

        LocalDate dueDate = calDueDate(borrowDate);

        record.setBorrowerUid(borrower.getUid());
        record.setBookNo(book.getBookNo());
        record.setBorrowDate(borrowDate);
        record.setDueDate(dueDate);

        record = recordRepo.save(record);

        return record;
    }

    public BorrowingRecord returnBook(long recNo) throws Exception {
        BorrowingRecord record = validateBorrowingRecord(recNo);

        LocalDate dueDate = record.getDueDate();
        LocalDate returnDate = LocalDate.now();

        record.setReturnDate(returnDate);

        if (returnDate.isAfter(dueDate)) {
            BigDecimal overdueFee = calOverdueFee(dueDate, returnDate);

            record.setOverdueFines(overdueFee);
        }

        record = recordRepo.save(record);
        return record;
    }

    private Borrower validateBorrower(String uid) throws Exception {
        Borrower borrower = new Borrower(uid, "John Doe");
        return borrower;
    }

    private Book validateBook(String bookNo) throws Exception {
        Book book = new Book(bookNo, "Book Title Sample", "Marry Peter");
        return book;
    }

    private BorrowingRecord validateBorrowingRecord(long recNo) throws Exception {
        Optional<BorrowingRecord> recordOpt = recordRepo.findById(recNo);
        BorrowingRecord record = recordOpt.orElseThrow(Exception::new);
        return record;
    }

    private LocalDate calDueDate(LocalDate borrowDate) {
        return borrowDate.plusDays(14);
    }

    private BigDecimal calOverdueFee(LocalDate dueDate, LocalDate returnDate) {
        long overDueDays = ChronoUnit.DAYS.between(dueDate, returnDate);

        BigDecimal overdueFee = BigDecimal.valueOf(overDueDays * 1.2d);

        return overdueFee;

    }

}

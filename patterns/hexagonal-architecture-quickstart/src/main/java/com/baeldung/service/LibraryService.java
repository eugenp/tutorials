package com.baeldung.service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.exception.BookNotFoundException;
import com.baeldung.exception.BorrowerNotFoundException;
import com.baeldung.exception.BorrowingRecordNotFoundException;
import com.baeldung.exception.RecordNotFoundException;
import com.baeldung.pojo.Book;
import com.baeldung.pojo.Borrower;
import com.baeldung.pojo.BorrowingRecord;
import com.baeldung.repo.BookRepository;
import com.baeldung.repo.BorrowerRepository;
import com.baeldung.repo.BorrowingRecordRepository;

@Service
public class LibraryService {

    @Autowired
    private BorrowerRepository borrowerRepo;

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private BorrowingRecordRepository recordRepo;

    public BorrowingRecord borrowBook(String borrowerUid, String bookNo) throws RecordNotFoundException {
        Optional<Borrower> borrowerOpt = borrowerRepo.findById(borrowerUid);
        Borrower borrower = borrowerOpt.orElseThrow(BorrowerNotFoundException::new);

        Optional<Book> bookOpt = bookRepo.findById(bookNo);
        Book book = bookOpt.orElseThrow(BookNotFoundException::new);

        BorrowingRecord record = new BorrowingRecord();

        LocalDate borrowDate = LocalDate.now();

        LocalDate dueDate = borrowDate.plusDays(14);
        LocalDate adjustedDueDate = adjustDueDate(dueDate);

        record.setBorrowerUid(borrower.getUid());
        record.setBookNo(book.getBookNo());
        record.setBorrowDate(borrowDate);
        record.setDueDate(adjustedDueDate);

        record = recordRepo.save(record);

        return record;
    }

    public BorrowingRecord returnBook(long recNo) throws RecordNotFoundException {
        Optional<BorrowingRecord> recordOpt = recordRepo.findById(recNo);
        BorrowingRecord record = recordOpt.orElseThrow(BorrowingRecordNotFoundException::new);

        LocalDate dueDate = record.getDueDate();
        LocalDate returnDate = LocalDate.now();

        record.setReturnDate(returnDate);

        if (returnDate.isAfter(dueDate)) {
            BigDecimal overdueFee = calOverdueFee(dueDate,returnDate);

            record.setOverdueFines(overdueFee);
        }

        record = recordRepo.save(record);
        return record;
    }

    private LocalDate adjustDueDate(LocalDate date) {
        return date;
    }
    
    private BigDecimal calOverdueFee(LocalDate dueDate, LocalDate returnDate) {
        long overDueDays = ChronoUnit.DAYS.between(dueDate, returnDate);

        BigDecimal overdueFee = BigDecimal.valueOf(overDueDays * 1.2d);

        return overdueFee;
        
    }

}

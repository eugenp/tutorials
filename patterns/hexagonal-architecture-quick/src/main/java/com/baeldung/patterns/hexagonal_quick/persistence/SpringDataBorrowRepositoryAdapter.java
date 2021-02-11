package com.baeldung.patterns.hexagonal_quick.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import com.baeldung.patterns.hexagonal_quick.exception.BookNotFoundException;
import com.baeldung.patterns.hexagonal_quick.exception.BorrowRecordNotFoundException;
import com.baeldung.patterns.hexagonal_quick.persistence.model.BorrowedBookData;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.baeldung.patterns.hexagonal_quick.domain.Book;
import com.baeldung.patterns.hexagonal_quick.domain.BorrowRecord;
import com.baeldung.patterns.hexagonal_quick.domain.BorrowedBook;
import com.baeldung.patterns.hexagonal_quick.persistence.model.BorrowRecordData;
import com.baeldung.patterns.hexagonal_quick.port.BorrowOutputPort;
import com.baeldung.patterns.hexagonal_quick.util.Converter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

public class SpringDataBorrowRepositoryAdapter implements BorrowOutputPort {

    private final MongoBorrowRepository mongoBorrowRepository;
    private final Converter<BorrowRecordData, BorrowRecord> borrowRecordConverter;

    public SpringDataBorrowRepositoryAdapter(
            MongoBorrowRepository borrowRepository, Converter<BorrowRecordData, BorrowRecord> borrowRecordConverter) {
        this.mongoBorrowRepository = borrowRepository;
        this.borrowRecordConverter = borrowRecordConverter;
    }

    @Override
    public Optional<BorrowRecord> findBorrowForBorrowId(String borrowId) {
        return mongoBorrowRepository.findById(borrowId)
            .map(borrowRecordConverter::convert);
    }

    @Override
    public Optional<BorrowRecord> findBorrowForUser(String username) {
        return mongoBorrowRepository.findByUsername(username)
                .map(borrowRecordConverter::convert);
    }

    @Override
    public BorrowRecord createNewBorrow(String username, BorrowedBook borrowedBook) {
        String borrowId = String.format("%d_%s", System.currentTimeMillis(), username);
        BorrowRecordData borrowRecordData = BorrowRecordData.createForOneBorrow(borrowId, username, borrowedBook);
        return borrowRecordConverter.convert(mongoBorrowRepository.save(borrowRecordData));
    }

    @Override
    public BorrowRecord addForExistingBorrow(String borrowId, BorrowedBook borrowedBook) {
        return mongoBorrowRepository.findById(borrowId)
                .map(record -> {
                    record.getBorrowedBooks().add(BorrowedBookData.createFrom(borrowedBook));
                    return mongoBorrowRepository.save(record);
                })
                .map(borrowRecordConverter::convert)
                .orElseThrow(() -> new BorrowRecordNotFoundException(borrowId));
    }

    @Override
    public void removeBookFromBorrow(String borrowId, Book book) {
        BorrowRecordData borrowRecord = mongoBorrowRepository.findById(borrowId)
                .orElseThrow(() -> new BorrowRecordNotFoundException(borrowId));

        Collection<BorrowedBookData> copyOfBorrowedBooks = new ArrayList<>(borrowRecord.getBorrowedBooks());
        for (BorrowedBookData borrowedBook : copyOfBorrowedBooks) {
            if (book.getIsbn().equals(borrowedBook.getBook().getIsbn())) {
                borrowRecord.getBorrowedBooks().remove(borrowedBook);
                break;
            }
        }

        if (borrowRecord.getBorrowedBooks().isEmpty()) {
            mongoBorrowRepository.deleteById(borrowId);
        } else {
            mongoBorrowRepository.save(borrowRecord);
        }
    }

    @Repository
    public interface MongoBorrowRepository extends MongoRepository<BorrowRecordData, String> {
        Optional<BorrowRecordData> findByUsername(String username);
    }
}

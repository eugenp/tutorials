package com.baeldung.patterns.hexagonal_quick.persistence.converter;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;

import com.baeldung.patterns.hexagonal_quick.domain.BorrowRecord;
import com.baeldung.patterns.hexagonal_quick.domain.BorrowedBook;
import com.baeldung.patterns.hexagonal_quick.persistence.model.BorrowRecordData;
import com.baeldung.patterns.hexagonal_quick.persistence.model.BorrowedBookData;
import com.baeldung.patterns.hexagonal_quick.util.Converter;

@Component
public class BorrowRecordDataToBorrowRecord implements Converter<BorrowRecordData, BorrowRecord> {

    private final BorrowedBookDataToBorrowedBook borrowedBookDataToBorrowedBookConverter;

    public BorrowRecordDataToBorrowRecord(BorrowedBookDataToBorrowedBook borrowedBookDataToBorrowedBookConverter) {
        this.borrowedBookDataToBorrowedBookConverter = borrowedBookDataToBorrowedBookConverter;
    }

    @Override
    public BorrowRecord convert(BorrowRecordData subject) {
        Collection<BorrowedBook> borrowedBooks = new ArrayList<>();
        for (BorrowedBookData borrowedBook : subject.getBorrowedBooks()) {
            borrowedBooks.add(borrowedBookDataToBorrowedBookConverter.convert(borrowedBook));
        }
        return new BorrowRecord(subject.getBorrowId(), borrowedBooks);
    }
}

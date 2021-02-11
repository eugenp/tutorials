package com.baeldung.patterns.hexagonal_quick.persistence.converter;

import org.springframework.stereotype.Component;

import com.baeldung.patterns.hexagonal_quick.domain.Book;
import com.baeldung.patterns.hexagonal_quick.domain.BorrowedBook;
import com.baeldung.patterns.hexagonal_quick.persistence.model.BookData;
import com.baeldung.patterns.hexagonal_quick.persistence.model.BorrowedBookData;
import com.baeldung.patterns.hexagonal_quick.util.Converter;

@Component
public class BorrowedBookDataToBorrowedBook implements Converter<BorrowedBookData, BorrowedBook> {

    private final Converter<BookData, Book> bookDataToBookConverter;

    public BorrowedBookDataToBorrowedBook(Converter<BookData, Book> bookDataToBookConverter) {
        this.bookDataToBookConverter = bookDataToBookConverter;
    }

    @Override
    public BorrowedBook convert(BorrowedBookData subject) {
        return new BorrowedBook(bookDataToBookConverter.convert(subject.getBook()), subject.getDueDate());
    }
}

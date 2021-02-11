package com.baeldung.patterns.hexagonal_quick.persistence.model;

import java.util.Date;

import com.baeldung.patterns.hexagonal_quick.domain.BorrowedBook;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BorrowedBookData {
    private BookData book;
    private Date dueDate;

    public static BorrowedBookData createFrom(BorrowedBook borrowedBook) {
        return new BorrowedBookData(BookData.createFrom(borrowedBook.getBook()), borrowedBook.getDueDate());
    }
}

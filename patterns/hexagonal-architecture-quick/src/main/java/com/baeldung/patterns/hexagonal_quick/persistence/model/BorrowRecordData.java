package com.baeldung.patterns.hexagonal_quick.persistence.model;

import java.util.Collection;
import java.util.Collections;

import com.baeldung.patterns.hexagonal_quick.domain.BorrowedBook;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BorrowRecordData {
    private String borrowId;
    private Collection<BorrowedBookData> borrowedBooks;

    public static BorrowRecordData createForOneBorrow(String borrowId, BorrowedBook borrowedBook) {
        return new BorrowRecordData(borrowId, Collections.singletonList(BorrowedBookData.createFrom(borrowedBook)));
    }
}

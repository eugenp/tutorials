package com.baeldung.patterns.hexagonal_quick.controller.model;

import java.util.ArrayList;
import java.util.Collection;

import com.baeldung.patterns.hexagonal_quick.domain.BorrowRecord;
import com.baeldung.patterns.hexagonal_quick.domain.BorrowedBook;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiBorrowResponse {
    private String referenceId;
    private Collection<ApiBorrowedBook> booksDue;

    public static ApiBorrowResponse createFrom(BorrowRecord borrowRecord) {
        Collection<ApiBorrowedBook> borrowedBooks = new ArrayList<>();
        for (BorrowedBook borrowedBook : borrowRecord.getBorrowedBooks()) {
            borrowedBooks.add(ApiBorrowedBook.createFrom(borrowedBook));
        }
        return new ApiBorrowResponse(borrowRecord.getBorrowId(), borrowedBooks);
    }
}
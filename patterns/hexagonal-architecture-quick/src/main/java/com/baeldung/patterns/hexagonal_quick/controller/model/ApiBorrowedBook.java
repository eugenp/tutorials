package com.baeldung.patterns.hexagonal_quick.controller.model;

import java.util.Date;

import com.baeldung.patterns.hexagonal_quick.domain.BorrowedBook;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiBorrowedBook {
    private ApiBook book;
    private Date dueDate;

    public static ApiBorrowedBook createFrom(BorrowedBook borrowedBook) {
        return new ApiBorrowedBook(ApiBook.createFrom(borrowedBook.getBook()), borrowedBook.getDueDate());
    }
}
package com.baeldung.patterns.hexagonal_quick.domain;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BorrowRecord {
    private String borrowId;
    private Collection<BorrowedBook> borrowedBooks;
}

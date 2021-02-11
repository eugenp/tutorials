package com.baeldung.patterns.hexagonal_quick.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BorrowedBook {
    private Book book;
    private Date dueDate;
}
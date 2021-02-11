package com.baeldung.patterns.hexagonal_quick.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Book {
    private String isbn;
    private String title;
    private String[] authors;
}
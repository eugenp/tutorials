package com.baeldung.patterns.hexagonal_quick.domain;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Book {
    private String isbn;
    private String title;
    private Collection<String> authors;
}
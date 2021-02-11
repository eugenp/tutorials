package com.baeldung.patterns.hexagonal_quick.controller.model;

import com.baeldung.patterns.hexagonal_quick.domain.Book;

import lombok.*;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiBook {
    private String isbnNumber;
    private String bookName;
    private Collection<String> authorNames;

    public static ApiBook createFrom(Book book) {
        return new ApiBook(book.getIsbn(), book.getTitle(), book.getAuthors());
    }
}

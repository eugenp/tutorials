package com.baeldung.patterns.hexagonal_quick.persistence.model;

import com.baeldung.patterns.hexagonal_quick.domain.Book;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BookData {
    private String isbn;
    private String title;
    private String[] authors;

    public static BookData createFrom(Book book) {
        return new BookData(book.getIsbn(), book.getTitle(), book.getAuthors());
    }
}

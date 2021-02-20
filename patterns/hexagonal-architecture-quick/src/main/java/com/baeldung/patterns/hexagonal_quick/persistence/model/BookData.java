package com.baeldung.patterns.hexagonal_quick.persistence.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.baeldung.patterns.hexagonal_quick.domain.Book;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Document("book")
public class BookData {
    @Id
    private String isbn;
    private String title;

    public static BookData createFrom(Book book) {
        return new BookData(book.getIsbn(), book.getTitle());
    }
}

package com.baeldung.patterns.hexagonal_quick.persistence.converter;

import org.springframework.stereotype.Component;

import com.baeldung.patterns.hexagonal_quick.domain.Book;
import com.baeldung.patterns.hexagonal_quick.persistence.model.BookData;
import com.baeldung.patterns.hexagonal_quick.util.Converter;

@Component
public class BookDataToBook implements Converter<BookData, Book> {

    @Override
    public Book convert(BookData subject) {
        return new Book(subject.getIsbn(), subject.getTitle(), subject.getAuthors());
    }
}

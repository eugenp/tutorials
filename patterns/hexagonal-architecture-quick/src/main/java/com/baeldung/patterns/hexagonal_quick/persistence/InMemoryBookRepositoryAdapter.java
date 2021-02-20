package com.baeldung.patterns.hexagonal_quick.persistence;

import java.util.HashMap;
import java.util.Map;

import com.baeldung.patterns.hexagonal_quick.domain.Book;
import com.baeldung.patterns.hexagonal_quick.persistence.model.BookData;
import com.baeldung.patterns.hexagonal_quick.port.BookOutputPort;

public class InMemoryBookRepositoryAdapter implements BookOutputPort {

    private final Map<String, BookData> storedBooks;

    public InMemoryBookRepositoryAdapter(Map<String, BookData> initialData) {
        this.storedBooks = new HashMap<>(initialData);
    }

    @Override
    public Book createBook(Book book) {
        storedBooks.put(book.getIsbn(), BookData.createFrom(book));
        return book;
    }
}
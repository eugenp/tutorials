package com.baeldung.patterns.hexagonal_quick.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.baeldung.patterns.hexagonal_quick.domain.Book;
import com.baeldung.patterns.hexagonal_quick.persistence.model.BookData;
import com.baeldung.patterns.hexagonal_quick.port.BookOutputPort;
import com.baeldung.patterns.hexagonal_quick.util.Converter;

public class InMemoryBookRepositoryAdapter implements BookOutputPort {

    private final Map<String, BookData> storedBooks;
    private final Converter<BookData, Book> bookDataToBookConverter;

    public InMemoryBookRepositoryAdapter
            (Map<String, BookData> initialData, Converter<BookData, Book> bookDataToBookConverter) {
        this.bookDataToBookConverter = bookDataToBookConverter;
        this.storedBooks = new HashMap<>();
        this.storedBooks.putAll(initialData);
    }

    @Override
    public Optional<Book> findBookByIsbn(String isbn) {
        final BookData bookData = storedBooks.get(isbn);
        if (bookData == null) {
            return Optional.empty();
        }
        return Optional.of(bookDataToBookConverter.convert(bookData));
    }

    @Override
    public Book createBook(Book book) {
        storedBooks.put(book.getIsbn(), BookData.createFrom(book));
        return book;
    }
}
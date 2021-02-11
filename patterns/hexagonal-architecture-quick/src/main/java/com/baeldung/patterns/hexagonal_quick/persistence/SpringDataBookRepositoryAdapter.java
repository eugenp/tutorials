package com.baeldung.patterns.hexagonal_quick.persistence;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.patterns.hexagonal_quick.domain.Book;
import com.baeldung.patterns.hexagonal_quick.persistence.model.BookData;
import com.baeldung.patterns.hexagonal_quick.port.BookOutputPort;
import com.baeldung.patterns.hexagonal_quick.util.Converter;

public class SpringDataBookRepositoryAdapter implements BookOutputPort {

    private final MongoBookRepository bookRepository;
    private final Converter<BookData, Book> bookDataConverter;

    public SpringDataBookRepositoryAdapter(MongoBookRepository bookRepository, Converter<BookData, Book> bookDataConverter) {
        this.bookRepository = bookRepository;
        this.bookDataConverter = bookDataConverter;
    }

    @Override
    public Optional<Book> findBookByIsbn(String isbn) {
        return bookRepository.findById(isbn)
            .map(bookDataConverter::convert);
    }

    @Override
    public Book createBook(Book book) {
        final BookData bookData = BookData.createFrom(book);
        return bookDataConverter.convert(bookRepository.save(bookData));
    }

    @Repository
    public interface MongoBookRepository extends MongoRepository<BookData, String> {
    }
}
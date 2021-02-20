package com.baeldung.patterns.hexagonal_quick.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.patterns.hexagonal_quick.domain.Book;
import com.baeldung.patterns.hexagonal_quick.persistence.model.BookData;
import com.baeldung.patterns.hexagonal_quick.port.BookOutputPort;

public class SpringDataBookRepositoryAdapter implements BookOutputPort {

    private final MongoBookRepository bookRepository;

    public SpringDataBookRepositoryAdapter(MongoBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book createBook(Book book) {
        final BookData bookData = BookData.createFrom(book);
        return convertBookData(bookRepository.save(bookData));
    }

    private Book convertBookData(BookData bookData) {
        return new Book(bookData.getIsbn(), bookData.getTitle());
    }

    @Repository
    public interface MongoBookRepository extends MongoRepository<BookData, String> {
    }
}
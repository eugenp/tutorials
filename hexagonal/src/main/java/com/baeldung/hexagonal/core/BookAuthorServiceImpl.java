package com.baeldung.hexagonal.core;

import com.baeldung.hexagonal.domain.AuthorDto;
import com.baeldung.hexagonal.domain.BookDto;
import com.baeldung.hexagonal.domain.BookAuthorPersistencePort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookAuthorServiceImpl implements BookAuthorService {

    private BookAuthorPersistencePort bookAuthorPersistencePort;

    public BookAuthorServiceImpl(BookAuthorPersistencePort bookAuthorPersistencePort) {
        this.bookAuthorPersistencePort = bookAuthorPersistencePort;
    }

    @Override
    public void addBook(BookDto bookDto) {
        bookAuthorPersistencePort.addBook(bookDto);
    }

    @Override
    public List<BookDto> getAllBook() {
        return bookAuthorPersistencePort.getAllBook();
    }

    @Override
    public void addAuthor(AuthorDto authorDto) {
        bookAuthorPersistencePort.addAuthor(authorDto);
    }

    @Override
    public List<AuthorDto> getAllAuthor() {
        return bookAuthorPersistencePort.getAllAuthor();
    }

}

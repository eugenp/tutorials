package com.baeldung.hexagonal.domain.services;

import com.baeldung.hexagonal.domain.data.Book;
import com.baeldung.hexagonal.domain.repo.BooksRepository;

import java.util.UUID;

public class BooksServiceImpl implements BooksService {

    private final BooksRepository booksRepository;

    public BooksServiceImpl(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Override
    public UUID createBook(String name, String author, String description) {
        final Book book = new Book(UUID.randomUUID(), name, author, description);
        booksRepository.save(book);
        return book.getId();
    }

    @Override
    public void deleteBook(UUID id) {
        booksRepository.delete(id);
    }

}

package com.baeldung.hexbookstore.core.service;

import com.baeldung.hexbookstore.core.BookStore;
import com.baeldung.hexbookstore.core.repository.BookStoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DomainService implements BookStoreService {

    private final BookStoreRepository bookStoreRepository;

    public DomainService(BookStoreRepository bookStoreRepository) {
        this.bookStoreRepository = bookStoreRepository;
    }

    @Override
    public void sellBook(Long book) {
        bookStoreRepository.delete(book);
    }

    @Override
    public void addBook(BookStore book) {
        bookStoreRepository.save(book);
    }

    @Override
    public List<BookStore> listAllBooks() {
        return bookStoreRepository.findAll();
    }
}

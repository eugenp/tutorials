package com.baeldung.hexagonalarchitecturejava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceAdapter implements BookService {
    @Autowired
    BookRepo bookRepo;

    @Override
    public void addBook(Book book) {
        bookRepo.save(book);
    }

    @Override
    public void removeBook(Long bookId) {
        bookRepo.deleteById(bookId);

    }
    @Override
    public List<Book> getAllBooks() {
        return (List<Book>) bookRepo.findAll();

    }
}

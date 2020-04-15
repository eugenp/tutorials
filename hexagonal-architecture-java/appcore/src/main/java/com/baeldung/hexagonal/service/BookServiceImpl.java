package com.baeldung.hexagonal.service;


import com.baeldung.hexagonal.domain.Book;
import com.baeldung.hexagonal.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BookServiceImpl implements BookService {


    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public int addBook(Book newBook) {
        return bookRepository.addBook(newBook);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    @Override
    public Optional<List<Book>> findBookByName(String name) {
        return bookRepository.findBookByName(name);
    }
}

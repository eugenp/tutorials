package com.baeldung.hexagonal.bookstore.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.bookstore.ports.BookRepository;
import com.baeldung.hexagonal.bookstore.ports.BookRestServicePort;

@Service
public class BookServiceImpl implements BookRestServicePort {

    @Autowired
    private BookRepository bookRepository;
    
    @Override
    public void createBook(Book book) {
        bookRepository.createBook(book);
    }

    @Override
    public Book getBook(String title) {
       return bookRepository.getBook(title);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

}

package com.baeldung.hexarchitecture.application;

import com.baeldung.hexarchitecture.application.port.outbound.BookRepository;
import com.baeldung.hexarchitecture.application.port.inbound.CreateBookCommand;
import com.baeldung.hexarchitecture.application.port.inbound.BookService;
import com.baeldung.hexarchitecture.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void createBook(CreateBookCommand createBookCommand) {
        bookRepository.createBook(createBookCommand);
    }

    @Override
    public Book getBook(String id) {
        return bookRepository.getBook(id);
    }

}

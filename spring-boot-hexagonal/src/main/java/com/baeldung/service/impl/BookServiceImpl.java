package com.baeldung.service.impl;

import java.util.List;

import com.baeldung.domain.Book;
import com.baeldung.repository.BookRepository;
import com.baeldung.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;;

    @Override
    public void createOne(Book book) {
        bookRepository.createOneBook(book);
    }

    @Override
    public Book findOne(Long id) {
        return bookRepository.findOneById(id);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAllBook();
    }
    
}

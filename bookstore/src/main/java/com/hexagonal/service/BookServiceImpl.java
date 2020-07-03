package com.hexagonal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexagonal.domain.Book;
import com.hexagonal.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void addBook(Book book) {
        bookRepository.add(book);

    }

    @Override
    public Book buyBook(String isbn) {

        return bookRepository.buy(isbn);
    }

    @Override
    public List<Book> listBooks() {

        return bookRepository.list();
    }

}

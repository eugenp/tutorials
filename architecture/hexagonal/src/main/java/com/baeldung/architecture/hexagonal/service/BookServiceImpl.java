package com.baeldung.architecture.hexagonal.service;

import com.baeldung.architecture.hexagonal.model.Book;
import com.baeldung.architecture.hexagonal.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service public class BookServiceImpl implements BookService {

        BookRepository bookRepository;

        @Autowired public BookServiceImpl(BookRepository bookRepository) {
                this.bookRepository = bookRepository;
        }

        @Override public List<Book> getAllBooks() {

                return bookRepository.findAll();

        }

        @Override public Book saveBook(Book book) {

                return bookRepository.save(book);

        }

        @Override public Book getBook(long isbn) {

                return bookRepository.findByIsbn(isbn).orElse(null);

        }
}

package com.baeldung.hexagonal.library.service;

import com.baeldung.hexagonal.library.domain.Book;
import com.baeldung.hexagonal.library.domain.BookSearchCriteria;
import com.baeldung.hexagonal.library.repository.BookRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(BookSearchCriteria criteria) {
        return bookRepository.findAll()
                .stream()
                .filter(book -> book.getTitle().toLowerCase().contains(criteria.getTitle().toLowerCase()))
                .collect(toList());
    }
}

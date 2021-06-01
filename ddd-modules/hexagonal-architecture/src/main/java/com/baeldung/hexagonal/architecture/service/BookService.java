package com.baeldung.hexagonal.architecture.service;

import com.baeldung.hexagonal.architecture.repository.Book;
import com.baeldung.hexagonal.architecture.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BookService {
    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookDTO findBook(final String name, final Integer shelfNo) {
        if (Objects.isNull(name) || Objects.isNull(shelfNo)) {
            throw new IllegalArgumentException("Name and/or shelfNo are null");
        }
        Book book = bookRepository.findByNameAndShelfNo(name, shelfNo)
            .orElseThrow(() -> new RuntimeException("Book not found"));
        return new BookDTO(book);
    }
}

package com.baeldung.java.hexagonal.ports.inbound;

import com.baeldung.java.hexagonal.model.Book;

import java.util.Optional;

public class BookService {

    private BookRepository bookRepository;

    public String createBook(String name) {
        validateBook(name);

        Book book = bookRepository.saveBook(new Book()
                .setName(name));

        return book.getId();
    }

    private void validateBook(String name) {
        Optional<Book> book = bookRepository.findByName(name);
        if (book.isPresent()) {
            throw new IllegalArgumentException(String.format("Book with name %s already exists", name));
        }
    }

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
}

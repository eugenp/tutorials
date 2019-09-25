package com.baeldung.hexagonal.architecture.core;

import com.baeldung.hexagonal.architecture.model.Book;
import com.baeldung.hexagonal.architecture.model.User;

import java.util.List;

public class Library implements LibraryInterface {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public Library(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.loadBooks();
    }

    @Override
    public void rentBook(long bookId, long userId) {
        Book book = bookRepository.loadBook(bookId);
        User user = userRepository.loadUser(userId);

        if (book.getRenter() != null) throw new IllegalArgumentException("Book is already rented");

        book.setRenter(user);
        bookRepository.saveBook(book);
    }
}

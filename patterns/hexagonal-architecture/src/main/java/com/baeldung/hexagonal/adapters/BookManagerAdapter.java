package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.domain.models.Book;
import com.baeldung.hexagonal.domain.ports.BookService;

import java.util.List;
import java.util.Optional;

public class BookManagerAdapter {

    private BookService bookService;

    public BookManagerAdapter(BookService bookService) {
        this.bookService = bookService;
    }

    public int addBook(Book book) {
        return bookService.addBook(book);
    }

    public List<Book> getBooks() {
        return bookService.getAllBooks();
    }

    public Optional<Book> getBook(int id) {
        return bookService.getBook(id);
    }
}

package com.baeldung.pattern.architecture.hexagonal.userside.userinterface;

import java.util.List;

import com.baeldung.pattern.architecture.hexagonal.domain.entity.Book;
import com.baeldung.pattern.architecture.hexagonal.domain.interactor.IBookService;


public class BookConsoleUI {
    private IBookService bookService;

    public BookConsoleUI(IBookService bookService) {
        this.bookService = bookService;
    }

    public void showBooks() {
        List<Book> books = bookService.getBooks();
        printBooksToConsole(books);
    }

    public void printBooksToConsole(List<Book> books) {
        // logic to print books to console goes here
    }
}

package com.baeldung.beaninjection.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.baeldung.beaninjection.model.Book;
import com.baeldung.beaninjection.service.BookService;

/**
 * This class is the controller for Book Services.
 * @Author Akshay Desale.
 */
public class BookController {

    private BookService bookService;

//    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    public Book getBook(String title) {
        return bookService.getBook(title);
    }

    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    public boolean addBook(String title, String author) {
        return bookService.addBook(title, author);
    }

    public boolean removeBook(String title) {
        return bookService.removeBook(title);
    }

    public boolean removeAllBooks() {
        return bookService.removeAllBooks();
    }

}

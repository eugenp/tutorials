package com.baeldung.beaninjection.controller;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.baeldung.beaninjection.model.Book;
import com.baeldung.beaninjection.service.BookService;

/**
 * This class is the controller for Book Services.
 * @Author Akshay Desale.
 */

@Component
public class BookController implements ApplicationContextAware{

    @Autowired
    private BookService bookService;
    
    ApplicationContext applicationContext;

    public BookController() {
    }
  
    
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
    
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }
    
    public BookService getBookService() {
        return bookService;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext= applicationContext;
        
    }

}

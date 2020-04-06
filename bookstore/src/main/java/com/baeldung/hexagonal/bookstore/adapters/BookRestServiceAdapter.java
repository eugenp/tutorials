package com.baeldung.hexagonal.bookstore.adapters;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.bookstore.core.Book;
import com.baeldung.hexagonal.bookstore.ports.BookRestServicePort;
import com.baeldung.hexagonal.bookstore.rest.BookRest;

@RestController
@RequestMapping("/book")
public class BookRestServiceAdapter implements BookRest{
 
    @Autowired
    private BookRestServicePort bookRestServicePort;
 
    @Override
    public void createBook(Book book) {
        bookRestServicePort.createBook(book);    
    }
 
    @Override
    public Book getBook(String book) {
        return bookRestServicePort.getBook(book);
    }
 
     @Override
     public List<Book> getAllBooks() {
         return bookRestServicePort.getAllBooks();
      }
}


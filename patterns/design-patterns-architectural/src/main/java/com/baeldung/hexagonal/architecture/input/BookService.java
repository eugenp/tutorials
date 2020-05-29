package com.baeldung.hexagonal.architecture.input;

import com.baeldung.hexagonal.architecture.domain.Book;

import java.util.List;


// inbound port
// contains all the business methods
public interface BookService {

    public String createBook(Book book);

    public void updateBook(Book book);

    public Book getBookById(String id);

    public List<Book> getAllBooks();

    public void deleteBook(String id);
}

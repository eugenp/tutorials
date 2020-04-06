package com.baeldung.hexagonal.bookstore.adapters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.baeldung.hexagonal.bookstore.core.Book;
import com.baeldung.hexagonal.bookstore.ports.BookRepository;

@Repository
public class BookRepositoryAdapter implements BookRepository{
 
    private Map<String, Book> bookStore = new HashMap<String, Book>();
 
    @Override
    public void createBook(Book book) {
        bookStore.put(book.getTitle(), book);
    }
 
    @Override
    public Book getBook(String title) {
        return bookStore.get(title);
    }
 
    @Override
    public List<Book> getAllBooks() {
        return bookStore.values().stream().collect(Collectors.toList());
    }
}

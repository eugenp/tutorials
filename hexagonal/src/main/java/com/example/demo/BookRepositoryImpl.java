package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl implements BookRepository{
    
    Map<String, Book> books = new HashMap<>();
    
    public Book getBook(String title) {
        return books.get(title);
    }
    
    public List<Book> getBooksByMatchingTitle(String title){
        List<Book> matchingBooks = new ArrayList<Book>();
        for (Map.Entry<String, Book> bookEntry : books.entrySet()) {
            if(bookEntry.getKey().contains(title))
                matchingBooks.add(bookEntry.getValue());
        }
        return matchingBooks;
    }
    
    public List<Book> listAllBooks(){
        return new ArrayList<>(books.values());
    }
    
    public void addBook(Book book) {
        books.put(book.getTitle(), book);
    }
}
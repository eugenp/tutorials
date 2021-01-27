package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Service;

public interface BookService{
    public Book getBook(String title);
    public List<Book> getBooksByMatchingTitle(String title);
    public List<Book> listAllBooks();
    public void addBook(Book book);
}
package com.example.demo;

import java.util.List;

public interface BookRepository{
    public Book getBook(String title);
    public List<Book> getBooksByMatchingTitle(String title);
    public List<Book> listAllBooks();
    public void addBook(Book book);
}
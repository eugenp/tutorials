package com.HexagonalArchitecture.BookApplication.Service;

import com.HexagonalArchitecture.BookApplication.Model.Book;

import java.util.List;

public interface BookService {
    public void createBook(Book book);
    
    public Book getBook(String name);
    
    public List<Book> listBook();
}
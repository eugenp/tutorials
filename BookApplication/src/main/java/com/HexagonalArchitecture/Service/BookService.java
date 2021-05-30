package com.HexagonalArchitecture.Service;

import com.HexagonalArchitecture.Model.Book;

import java.util.List;

public interface BookService{
    public void createBook(Book book);
    public Book getBook(String name);
    public List<Book> listBook();
}
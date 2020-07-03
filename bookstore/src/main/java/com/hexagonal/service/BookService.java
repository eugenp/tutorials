package com.hexagonal.service;

import java.util.List;

import com.hexagonal.domain.Book;

public interface BookService {

    public void addBook(Book book);

    public Book buyBook(String isbn);

    public List<Book> listBooks();

}

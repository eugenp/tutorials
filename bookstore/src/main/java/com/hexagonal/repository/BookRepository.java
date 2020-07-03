package com.hexagonal.repository;

import java.util.List;

import com.hexagonal.domain.Book;

public interface BookRepository {

    public void add(Book book);

    public Book buy(String isbn);

    public List<Book> list();

}

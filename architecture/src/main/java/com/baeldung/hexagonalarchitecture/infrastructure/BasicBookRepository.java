package com.baeldung.hexagonalarchitecture.infrastructure;

import java.util.ArrayList;
import java.util.List;

import com.baeldung.hexagonalarchitecture.domain.Book;
import com.baeldung.hexagonalarchitecture.domain.IBookRepository;

public class BasicBookRepository implements IBookRepository {
    @Override
    public List<Book> findAllBooks() {
        List<Book> books = new ArrayList<Book>();
        books.add(new Book("9780136083238", "Clean Code: A Handbook of Agile Software Craftsmanship"));
        return books;
    }
}
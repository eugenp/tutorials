package com.hexagonal.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.hexagonal.domain.Book;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private Map<String, Book> bookMap = new HashMap<>();

    @Override
    public void add(Book book) {
        bookMap.put(book.getIsbn(), book);
        System.out.println("Book Added " + book);
    }

    @Override
    public Book buy(String isbn) {
        return bookMap.get(isbn);
    }

    @Override
    public List<Book> list() {
        return bookMap.values()
            .stream()
            .collect(Collectors.toList());
    }

}

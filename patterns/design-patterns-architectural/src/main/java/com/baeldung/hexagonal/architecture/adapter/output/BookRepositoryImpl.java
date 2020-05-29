package com.baeldung.hexagonal.architecture.adapter.output;

import com.baeldung.hexagonal.architecture.domain.Book;
import com.baeldung.hexagonal.architecture.output.BookRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// adapter
public class BookRepositoryImpl implements BookRepository {

    private Map<String, Book> database = new HashMap<>();

    @Override
    public Book getBookById(String id) {
        return database.get(id);
    }

    @Override
    public void saveBook(Book book) {
        database.putIfAbsent(book.getId(), book);
    }

    @Override
    public void updateBook(Book book) {
        database.put(book.getId(), book);
    }

    @Override
    public void deleteBook(String id) {
        database.remove(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(database.values());
    }
}

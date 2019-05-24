package org.hexagonal.architechture.adapters;

import java.util.Arrays;
import java.util.List;

import org.hexagonal.architechture.core.Book;
import org.hexagonal.architechture.core.BookRepository;

public class InMemoryBookRepository implements BookRepository {
    
    @Override
    public List<Book> getAllBooks() {
        return Arrays.asList(new Book("Design Patterns"), new Book("Clean Code"), new Book("Cloud Native Java"));
    }

}

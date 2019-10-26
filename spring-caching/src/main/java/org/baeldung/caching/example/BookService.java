package org.baeldung.caching.example;

import org.baeldung.caching.model.Book;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookService {

    @Cacheable(value="books", keyGenerator="customKeyGenerator")
    public List<Book> getBooks() {
        List<Book> books = new ArrayList<Book>();
        books.add(new Book(1, "The Counterfeiters", "André Gide"));
        books.add(new Book(2, "Peer Gynt and Hedda Gabler", "Henrik Ibsen"));
        return books;
    }

}

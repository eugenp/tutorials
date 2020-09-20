package com.baeldung.adapters.mock;

import com.baeldung.ports.BookRepository;
import domain.Book;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BookMockRepository implements BookRepository {

    private final Book book1 = new Book("12312-5fdauu1231", "Shakes", "Romeu", 1991);
    private final Book book2 = new Book("903238-8kkfdalie", "Cleaner code", "Aunt alice", 2001);
    private final Book book3 = new Book("102109-9irieiqioort", "Shakes", "Juliet", 1995);



    public Book getBookById(String id) {
        return findAllBooks().stream().filter(book -> book.getId().equalsIgnoreCase(id)).collect(Collectors.toList()).get(0);
    }

    public List<Book> findAllBooksFromAuthor(final String author) {
        return findAllBooks().stream().filter(book -> book.getAuthor().equalsIgnoreCase(author)).collect(Collectors.toList());
    }

    private List<Book> findAllBooks() {
        return Arrays.asList(book1, book2, book3);
    }
}

package org.baeldung.boot.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.baeldung.boot.domain.Book;
import org.baeldung.boot.domain.BookRepository;

public class InMemoryBookRepositoryImpl implements BookRepository {

    private List<Book> inMemoryStorage;

    public InMemoryBookRepositoryImpl() {
        this.inMemoryStorage = new ArrayList<Book>();
    }

    @Override
    public void storeBook(Book book) {
        inMemoryStorage.add(book);
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return inMemoryStorage.stream()
                              .filter(b -> b.getAuthor().equals(author))
                              .collect(Collectors.toList());
    }

}

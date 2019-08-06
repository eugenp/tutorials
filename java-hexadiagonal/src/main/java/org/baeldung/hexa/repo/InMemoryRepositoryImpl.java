package org.baeldung.hexa.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.baeldung.hexa.domain.Book;
import org.baeldung.hexa.domain.BookRepository;

import com.google.common.collect.ImmutableList;

public class InMemoryRepositoryImpl implements BookRepository {

    private List<Book> inMemoryStorage;

    public InMemoryRepositoryImpl() {
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

    @Override
    public List<Book> getAllBooks() {
        return ImmutableList.copyOf(inMemoryStorage);
    }
    

}

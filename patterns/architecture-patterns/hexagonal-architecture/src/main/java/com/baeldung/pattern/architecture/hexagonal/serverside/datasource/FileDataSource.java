package com.baeldung.pattern.architecture.hexagonal.serverside.datasource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.baeldung.pattern.architecture.hexagonal.domain.entity.Book;
import com.baeldung.pattern.architecture.hexagonal.domain.repository.BookRepository;

public class FileDataSource implements BookRepository {

    @Override
    public List<Book> getBooks() {
        return getBooksFromFile().stream()
            .map(this::mapBookStringToBook)
            .collect(Collectors.toList());
    }

    public List<String> getBooksFromFile() {
        // code for reading book data from file goes here
        return new ArrayList<>();
    }

    private Book mapBookStringToBook(String bookString) {
        String[] bookDetails = bookString.split(";");
        return new Book(Integer.parseInt(bookDetails[0]), bookDetails[1], bookDetails[2]);
    }
}

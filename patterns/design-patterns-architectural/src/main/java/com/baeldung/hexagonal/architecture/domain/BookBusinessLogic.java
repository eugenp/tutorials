package com.baeldung.hexagonal.architecture.domain;

import com.baeldung.hexagonal.architecture.port.in.BookService;
import com.baeldung.hexagonal.architecture.port.out.BookRepository;
import com.baeldung.hexagonal.architecture.port.out.BookWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookBusinessLogic implements BookService {
    private BookRepository repository;
    private BookWriter writer;

    public BookBusinessLogic(BookRepository repository, BookWriter writer) {
        this.repository = repository;
        this.writer = writer;
    }
    @Override
    public void getBooks() {
        List<Book> books = repository.getAllBooks();
        List<Book> shuffled = new ArrayList<>(books);
        Collections.shuffle(shuffled);
        writer.writeBooks(shuffled);
    }
}

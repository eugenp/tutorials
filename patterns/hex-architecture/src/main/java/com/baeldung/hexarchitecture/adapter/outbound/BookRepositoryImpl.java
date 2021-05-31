package com.baeldung.hexarchitecture.adapter.outbound;

import com.baeldung.hexarchitecture.application.port.outbound.BookRepository;
import com.baeldung.hexarchitecture.application.port.inbound.CreateBookCommand;
import com.baeldung.hexarchitecture.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private Map<String, Book> bookDB = new HashMap<>();

    @Override
    public void createBook(CreateBookCommand createBookCommand) {
        Book book = new Book();
        book.setAuthor(createBookCommand.getAuthor());
        book.setName(createBookCommand.getName());
        book.setId(UUID.randomUUID().toString());

        bookDB.put(book.getId(), book);
    }

    @Override
    public Book getBook(String id) {
        return bookDB.get(id);
    }
}

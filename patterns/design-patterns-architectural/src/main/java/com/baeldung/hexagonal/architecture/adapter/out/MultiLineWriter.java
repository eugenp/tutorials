package com.baeldung.hexagonal.architecture.adapter.out;

import com.baeldung.hexagonal.architecture.domain.Book;
import com.baeldung.hexagonal.architecture.port.out.BookWriter;

import java.util.List;

public class MultiLineWriter implements BookWriter {
    @Override
    public void writeBooks(List<Book> books) {
        books.forEach(book -> {
            System.out.println("******");
            System.out.println(book.getTitle());
            System.out.println(book.getAuthor());
            System.out.println(book.getIsbn());
            System.out.println("******");
        });
    }
}

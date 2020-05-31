package com.baeldung.hexagonal.architecture.adapter.out;

import com.baeldung.hexagonal.architecture.domain.Book;
import com.baeldung.hexagonal.architecture.port.out.BookWriter;

import java.util.List;

public class MultiLineConsoleWriter implements BookWriter {
    @Override
    public void writeBooks(List<Book> books) {
        books.forEach(book -> {
            System.out.println("Title:" + book.getTitle());
            System.out.println("Author:" + book.getAuthor());
            System.out.println("ISBN:" + book.getIsbn());
            System.out.println("******");
        });
    }
}

package com.baeldung.hexagonal.architecture.adapter.outgoing;

import com.baeldung.hexagonal.architecture.domain.Book;
import com.baeldung.hexagonal.architecture.port.outgoing.BookWriter;

import java.util.List;

public class SingleLineConsoleWriter implements BookWriter {
    @Override
    public void writeBooks(List<Book> books) {
      books.forEach(book -> {
          System.out.println("Title: " + book.getTitle() + ", "
                           + "Author: " + book.getAuthor());
      });
    }
}

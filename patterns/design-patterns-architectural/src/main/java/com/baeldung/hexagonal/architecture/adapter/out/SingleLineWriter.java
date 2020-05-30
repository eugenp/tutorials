package com.baeldung.hexagonal.architecture.adapter.out;

import com.baeldung.hexagonal.architecture.domain.Book;
import com.baeldung.hexagonal.architecture.port.out.BookWriter;

import java.util.List;

public class SingleLineWriter implements BookWriter {
    @Override
    public void writeBooks(List<Book> books) {
      books.forEach(book -> {
          System.out.println("Title: " + book.getTitle()
                           + "Author: " + book.getAuthor()
                           + "ISBN: " + book.getIsbn());
      });
    }
}

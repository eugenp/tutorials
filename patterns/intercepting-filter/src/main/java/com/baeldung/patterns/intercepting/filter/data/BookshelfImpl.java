package com.baeldung.patterns.intercepting.filter.data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookshelfImpl extends ArrayList<Book> implements Bookshelf {
    @Override
    public Book get(String isbn) {
        return this.stream()
          .filter(book -> book.getIsbn().equals(isbn))
          .findFirst()
          .orElse(null);
    }

    @Override
    public List<Book> find(String q) {
        return this.stream()
          .filter(book -> book.getTitle().toLowerCase().contains(q.toLowerCase())
            || book.getAuthor().toLowerCase().contains(q.toLowerCase()))
          .collect(Collectors.toList());
    }
}

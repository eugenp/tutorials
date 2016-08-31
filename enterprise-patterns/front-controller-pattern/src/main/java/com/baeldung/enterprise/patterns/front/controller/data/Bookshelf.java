package com.baeldung.enterprise.patterns.front.controller.data;

import java.util.ArrayList;
import java.util.List;

public class Bookshelf {
    private static Bookshelf INSTANCE = new Bookshelf();
    private List<Book> books = new ArrayList<>();

    public static Bookshelf getInstance() {
        if (INSTANCE.books.size() == 0) {
            INSTANCE.init();
        }
        return INSTANCE;
    }

    private void init() {
        books.add(new Book("Wilson, Robert Anton & Shea, Robert", "Illuminati", 9.99));
        books.add(new Book("Fowler, Martin", "Patterns of Enterprise Application Architecture", 27.88));
    }

    public Book findByTitle(String title) {
        return books.stream()
          .filter(book -> book.getTitle().equalsIgnoreCase(title))
          .findFirst()
          .orElse(null);
    }

    public List<Book> getBooks() {
        return books;
    }
}

package com.baeldung.patterns.front.controller.data;

import java.util.ArrayList;

public class BookshelfImpl extends ArrayList<Book> implements Bookshelf {
    private static Bookshelf INSTANCE;

    @Override
    public Bookshelf getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookshelfImpl();
            INSTANCE.init();
        }
        return INSTANCE;
    }

    @Override
    public Book findByTitle(String title) {
        return this.stream()
          .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
          .findFirst()
          .orElse(null);
    }
}

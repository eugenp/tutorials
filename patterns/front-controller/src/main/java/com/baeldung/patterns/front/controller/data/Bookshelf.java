package com.baeldung.patterns.front.controller.data;

public interface Bookshelf {

    default void init() {
        add(new BookImpl("Wilson, Robert Anton & Shea, Robert", "Illuminati", 9.99));
        add(new BookImpl("Fowler, Martin", "Patterns of Enterprise Application Architecture", 27.88));
    }

    Bookshelf getInstance();

    <E extends Book> boolean add(E book);

    Book findByTitle(String title);
}

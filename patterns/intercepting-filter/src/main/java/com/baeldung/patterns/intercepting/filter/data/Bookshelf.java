package com.baeldung.patterns.intercepting.filter.data;

import java.util.List;

public interface Bookshelf {

    default void init() {
        add(new BookImpl("001", "Wilson, Robert Anton & Shea, Robert", "Illuminati", 9.99));
        add(new BookImpl("002", "Fowler, Martin", "Patterns of Enterprise Application Architecture", 27.88));
        add(new BookImpl("003", "Unknown", "Something about German Umlauts (äüö) and ß", 5.49));
    }

    <E extends Book> boolean add(E book);

    Book get(String isbn);

    List<Book> find(String q);
}

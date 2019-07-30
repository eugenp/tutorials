package com.baeldung.hexagonal.arch.inside;

import java.util.List;

public interface Bookstore {

    void save(String title);

    List<Book> getBooks();

}

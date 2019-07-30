package com.baeldung.hexagonal.arch.inside;

import java.util.List;

public interface Bookstore {

    void create(String title);

    List<Book> getBooks();

}

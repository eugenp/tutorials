package com.baeldung.hexagonal.port;

import com.baeldung.hexagonal.model.Book;

public interface BookInboundPort {
    void createBook(Book book);

    Book getBook(String title);
}

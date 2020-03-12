package com.baeldung.hexagonal.port;

import com.baeldung.hexagonal.model.Book;

public interface BookOutboundPort {
    void saveBook(Book book);

    Book getBook(String title);
}

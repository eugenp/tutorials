package com.baeldung.hexagonal.port;

import com.baeldung.hexagonal.domain.Book;

public interface BookInboundPort {

    void add(Book book);

    Book search(String bookName);
}

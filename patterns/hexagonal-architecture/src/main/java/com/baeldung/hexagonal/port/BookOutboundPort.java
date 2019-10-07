package com.baeldung.hexagonal.port;

import com.baeldung.hexagonal.domain.Book;

public interface BookOutboundPort {

    void addBook(Book book);

    Book getBook(String bookName);
}
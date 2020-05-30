package com.baeldung.hexagonal.architecture.port.out;

import com.baeldung.hexagonal.architecture.domain.Book;

import java.util.List;

public interface BookWriter {
    public void writeBooks(List<Book> books);
}

package com.baeldung.hexagonal;

import java.util.List;

class ConsolePrintBookListImpl implements PrintBooksList {

    @Override
    public void printBooksList(List<Book> books) {
        books.forEach(book -> System.out.println(book.toString()));
    }
}
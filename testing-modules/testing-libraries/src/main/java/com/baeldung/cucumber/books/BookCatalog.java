package com.baeldung.cucumber.books;

import java.util.ArrayList;
import java.util.List;

public class BookCatalog {

    private List<Book> books = new ArrayList<>();
    
    public void addBook(Book book) {
        books.add(book);
    }
    
    public List<Book> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        return "BookCatalog [books=" + books + "]";
    }
}

package com.baeldung.ditypes;

import org.springframework.stereotype.Service;

@Service("bookServiceImpl")
public class BookServiceImpl implements BookService {
    public Boolean create(Book book) {
        return true;
    }

    public Book read(String isbn) {
        return new Book("Title", "1234SDK");
    }

    public Boolean update(String isbn, Book book) {
        return true;
    }

    public Boolean delete(String isbn) {
        return true;
    }
}

package com.baeldung.hexagonal.bookstore.secondaryport;

import com.baeldung.hexagonal.bookstore.entity.Book;
import com.baeldung.hexagonal.bookstore.jpa.BookStoreData;

public class BookStoreDatabaseRepository implements BookStoreRepository {

    @Override
    public Book findBookByIsbn(String isbnNumber) {
        return BookStoreData.getBook(isbnNumber);
    }
}

package com.baeldung.hexagonal.bookstore.secondaryport;

import com.baeldung.hexagonal.bookstore.model.Book;
import com.baeldung.hexagonal.bookstore.utils.BookStoreData;

public class BookStoreDatabaseRepository implements BookStoreRepository {

    @Override
    public Book findBookByIsbn(String isbnNumber) {
        return BookStoreData.fetchBook(isbnNumber);
    }
}

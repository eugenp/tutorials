package com.baeldung.hexagonalarchitecture.contract;

import com.baeldung.hexagonalarchitecture.model.Book;

import java.util.List;

public interface BooksService {

    List<Book> retrieveBooks();
}

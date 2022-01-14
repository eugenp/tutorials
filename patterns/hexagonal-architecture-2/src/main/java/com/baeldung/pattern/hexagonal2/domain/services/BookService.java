package com.baeldung.pattern.hexagonal2.domain.services;

import com.baeldung.pattern.hexagonal2.domain.model.Book;

public interface BookService {

    Book getBook(String isbn);

}

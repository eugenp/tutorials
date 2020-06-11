package com.baeldung.hexagonal.domain.services;

import com.baeldung.hexagonal.domain.repo.BooksRepository;
import com.baeldung.hexagonal.domain.services.impl.BooksServiceImpl;

public class BooksServiceFactory {

    public static BooksService getBooksService(BooksRepository booksRepository) {
        return new BooksServiceImpl(booksRepository);
    }

}

package com.baeldung.hexagonal.bookfinder.service;

import com.baeldung.hexagonal.bookfinder.domain.Book;
import com.baeldung.hexagonal.bookfinder.domain.Store;
import com.baeldung.hexagonal.bookfinder.port.BookLocator;
import com.baeldung.hexagonal.bookfinder.port.StoreLocator;
import com.baeldung.hexagonal.bookfinder.port.BookFinder;

public class BookFinderService implements BookFinder {

    private BookLocator bookLocator;
    private StoreLocator storeLocator;

    public BookFinderService(BookLocator bookLocator, StoreLocator storeLocator) {
        this.bookLocator = bookLocator;
        this.storeLocator = storeLocator;
    }

    @Override
    public Store findStoreForBookWithPassage(String passage) {
        Book book = bookLocator.findBookWithPassage(passage);
        return storeLocator.findStoreWithBook(book);
    }

}

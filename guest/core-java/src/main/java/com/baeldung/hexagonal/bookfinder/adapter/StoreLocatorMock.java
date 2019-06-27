package com.baeldung.hexagonal.bookfinder.adapter;

import java.util.ArrayList;

import com.baeldung.hexagonal.bookfinder.domain.Book;
import com.baeldung.hexagonal.bookfinder.domain.Store;
import com.baeldung.hexagonal.bookfinder.port.StoreLocator;

public class StoreLocatorMock implements StoreLocator {

    Store store;

    public StoreLocatorMock() {
        setupStore();
    }

    private void setupStore() {
        store = new Store();
        store.setBooks(new ArrayList<>());
        store.getBooks()
            .add(new Book("Introduction to Spring", "Author", "ISB0000223"));
        store.getBooks()
            .add(new Book("Introduction to Java", "James Gosling", "ISB0000258"));

    }

    @Override
    public Store findStoreWithBook(Book book) {
        if (this.store.getBooks()
            .contains(book)) {
            return this.store;
        }
        return null;
    }

}

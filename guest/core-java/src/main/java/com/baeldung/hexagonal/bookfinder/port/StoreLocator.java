package com.baeldung.hexagonal.bookfinder.port;

import com.baeldung.hexagonal.bookfinder.domain.Book;
import com.baeldung.hexagonal.bookfinder.domain.Store;

public interface StoreLocator {

    Store findStoreWithBook(Book book);
}

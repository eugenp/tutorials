package com.baeldung.hexagonal.bookfinder.adapter;

import com.baeldung.hexagonal.bookfinder.domain.Store;
import com.baeldung.hexagonal.bookfinder.port.BookFinder;

public class UserInterfaceAdapter {

    private BookFinder userInterface;

    public UserInterfaceAdapter(BookFinder userInterface) {
        this.userInterface = userInterface;
    }

    public Store getStore(String passage) {
        return this.userInterface.findStoreForBookWithPassage(passage);
    }
}

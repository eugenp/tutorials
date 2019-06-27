package com.baeldung.hexagonal.bookfinder.client;

import com.baeldung.hexagonal.bookfinder.adapter.BookLocatorMock;
import com.baeldung.hexagonal.bookfinder.adapter.StoreLocatorMock;
import com.baeldung.hexagonal.bookfinder.adapter.UserInterfaceAdapter;
import com.baeldung.hexagonal.bookfinder.port.BookFinder;
import com.baeldung.hexagonal.bookfinder.port.BookLocator;
import com.baeldung.hexagonal.bookfinder.port.StoreLocator;
import com.baeldung.hexagonal.bookfinder.service.BookFinderService;

public class Client {

    public static void main(String[] args) {
        BookLocator bookLocator = new BookLocatorMock();
        StoreLocator storeLocator = new StoreLocatorMock();

        BookFinder bookFinder = new BookFinderService(bookLocator, storeLocator);

        UserInterfaceAdapter userInterface = new UserInterfaceAdapter(bookFinder);

        System.out.println(userInterface.getStore("Java"));

    }
}

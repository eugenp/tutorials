package com.baeldung.hexagonal.infrastructure;

import com.baeldung.hexagonal.application.ConsoleApp;
import com.baeldung.hexagonal.domain.services.BooksService;
import com.baeldung.hexagonal.domain.services.BooksServiceFactory;
import com.baeldung.hexagonal.infrastructure.repo.BooksRepositoryImpl;

public class ApplicationMain {

    public static void main(String[] args) {
        BooksService booksService = BooksServiceFactory.getBooksService(new BooksRepositoryImpl());
        ConsoleApp consoleApp = new ConsoleApp(System.console(), booksService);
        consoleApp.waitForCommand();
    }

}

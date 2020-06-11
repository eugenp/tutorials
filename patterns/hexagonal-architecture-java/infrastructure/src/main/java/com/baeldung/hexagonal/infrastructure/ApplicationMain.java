package com.baeldung.hexagonal.infrastructure;

import com.baeldung.hexagonal.application.ConsoleApp;
import com.baeldung.hexagonal.domain.services.BooksService;
import com.baeldung.hexagonal.domain.services.ServiceFactory;
import com.baeldung.hexagonal.infrastructure.repo.BooksRepositoryImpl;

public class ApplicationMain {
    
    public static void main(String[] args) {
        BooksService booksService = ServiceFactory.getBooksService(new BooksRepositoryImpl());
        ConsoleApp consoleApp = new ConsoleApp(System.console(), booksService);
        consoleApp.waitForCommand();
    }
    
}

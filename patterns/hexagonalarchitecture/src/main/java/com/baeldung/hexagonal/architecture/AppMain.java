package com.baeldung.hexagonal.architecture;

import com.baeldung.hexagonal.architecture.application.Client;
import com.baeldung.hexagonal.architecture.domain.DomainBookService;
import com.baeldung.hexagonal.architecture.infrastructure.BookLibrary;

public class AppMain {

    public static void main(String[] args) {
        new AppMain().run();
    }

    private void run() {
        BookLibrary bookLibrary = new BookLibrary();

        Client client = new Client(new DomainBookService(bookLibrary));

        System.out.println("Book with isbn: " + client.addBook() + " added");
        System.out.println(client.searchForBook());
    }
}

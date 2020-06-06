package com.baeldung.hexagonal;

import com.baeldung.hexagonal.adapter.incoming.Command;
import com.baeldung.hexagonal.adapter.outgoing.InMemoryBookDatabase;
import com.baeldung.hexagonal.adapter.outgoing.SingleLineConsoleWriter;
import com.baeldung.hexagonal.domain.BookServiceDomain;
import com.baeldung.hexagonal.port.incoming.BookService;
import com.baeldung.hexagonal.port.outgoing.BookRepository;
import com.baeldung.hexagonal.port.outgoing.BookWriter;

public class Application {

    public static void main(String[] args) {
        BookRepository bookRepository = new InMemoryBookDatabase();
        BookWriter bookWriter = new SingleLineConsoleWriter();
        BookService bookService = new BookServiceDomain(bookRepository, bookWriter);
        Command command = new Command(bookService);
        command.run();
    }
}

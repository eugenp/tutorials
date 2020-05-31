package com.baeldung.hexagonal.architecture;

import com.baeldung.hexagonal.architecture.adapter.incoming.Command;
import com.baeldung.hexagonal.architecture.adapter.outgoing.InMemoryBookDatabase;
import com.baeldung.hexagonal.architecture.adapter.outgoing.SingleLineConsoleWriter;
import com.baeldung.hexagonal.architecture.domain.BookServiceDomain;
import com.baeldung.hexagonal.architecture.port.incoming.BookService;
import com.baeldung.hexagonal.architecture.port.outgoing.BookRepository;
import com.baeldung.hexagonal.architecture.port.outgoing.BookWriter;

public class Application {

    public static void main(String[] args) {
        BookRepository inMemoryBookRepository = new InMemoryBookDatabase();
        BookWriter singleLineWriter = new SingleLineConsoleWriter();
        BookService singleLineBookService = new BookServiceDomain(inMemoryBookRepository, singleLineWriter);
        Command singleLineCommand = new Command(singleLineBookService);
        singleLineCommand.run();
    }
}

package com.baeldung.hexagonal.architecture;

import com.baeldung.hexagonal.architecture.adapter.in.Command;
import com.baeldung.hexagonal.architecture.adapter.out.InMemoryBookDatabase;
import com.baeldung.hexagonal.architecture.adapter.out.MultiLineConsoleWriter;
import com.baeldung.hexagonal.architecture.adapter.out.SingleLineConsoleWriter;
import com.baeldung.hexagonal.architecture.domain.BookServiceDomain;
import com.baeldung.hexagonal.architecture.port.in.BookService;
import com.baeldung.hexagonal.architecture.port.out.BookRepository;
import com.baeldung.hexagonal.architecture.port.out.BookWriter;

public class Application {

    public static void main(String[] args) {
        // MultiLineWriter
        BookRepository inMemoryBookRepository = new InMemoryBookDatabase();
        BookWriter multiLineWriter = new MultiLineConsoleWriter();
        BookService multiLineBookService = new BookServiceDomain(inMemoryBookRepository, multiLineWriter);
        Command multiLineCommand = new Command(multiLineBookService);
        multiLineCommand.run();

        // SingleLineWriter
        BookWriter singleLineWriter = new SingleLineConsoleWriter();
        BookService singleLineBookService = new BookServiceDomain(inMemoryBookRepository, singleLineWriter);
        Command singleLineCommand = new Command(singleLineBookService);
        singleLineCommand.run();

    }
}

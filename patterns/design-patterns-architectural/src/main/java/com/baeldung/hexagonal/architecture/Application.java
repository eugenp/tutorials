package com.baeldung.hexagonal.architecture;

import com.baeldung.hexagonal.architecture.adapter.in.Command;
import com.baeldung.hexagonal.architecture.adapter.out.InMemoryBookDatabase;
import com.baeldung.hexagonal.architecture.adapter.out.MultiLineWriter;
import com.baeldung.hexagonal.architecture.adapter.out.SingleLineWriter;
import com.baeldung.hexagonal.architecture.domain.BookBusinessLogic;
import com.baeldung.hexagonal.architecture.port.in.BookService;
import com.baeldung.hexagonal.architecture.port.out.BookRepository;
import com.baeldung.hexagonal.architecture.port.out.BookWriter;

public class Application {

    public static void main(String[] args) {
        // MultiLineWriter
        BookRepository inMemoryBookRepository = new InMemoryBookDatabase();
        BookWriter multiLineWriter = new MultiLineWriter();
        BookService multiLineBookService = new BookBusinessLogic(inMemoryBookRepository, multiLineWriter);
        Command multiLineCommand = new Command(multiLineBookService);
        multiLineCommand.run();

        // SingleLineWriter
        BookWriter singleLineWriter = new SingleLineWriter();
        BookService singleLineBookService = new BookBusinessLogic(inMemoryBookRepository, singleLineWriter);
        Command singleLineCommand = new Command(singleLineBookService);
        singleLineCommand.run();

    }
}

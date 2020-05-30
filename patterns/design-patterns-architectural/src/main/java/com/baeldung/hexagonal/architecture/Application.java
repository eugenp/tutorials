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
        BookRepository inMemoryBookRepository = new InMemoryBookDatabase();
        BookWriter multiLineWriter = new MultiLineWriter();
        BookService bookService = new BookBusinessLogic(inMemoryBookRepository, multiLineWriter);

        Command command = new Command(bookService);
        command.run();


        BookWriter singleLineWriter = new SingleLineWriter();
        BookService bookService2 = new BookBusinessLogic(inMemoryBookRepository, singleLineWriter);

        Command command2 = new Command(bookService2);
        command2.run();

    }
}

package org.baeldung.hexa;

import java.io.IOException;

import org.baeldung.hexa.cli.InputParser;
import org.baeldung.hexa.cli.CliInputParserImpl;
import org.baeldung.hexa.domain.BookRepository;
import org.baeldung.hexa.domain.BookService;
import org.baeldung.hexa.domain.BookServiceImpl;
import org.baeldung.hexa.repo.InMemoryRepositoryImpl;

public class Application {

    public static void main(String[] args) throws IOException {

        BookRepository bookRepository = new InMemoryRepositoryImpl();
        BookService bookService = new BookServiceImpl(bookRepository);
        InputParser cliTransformer = new CliInputParserImpl(bookService);
        cliTransformer.processUserInput();

    }
}

package org.baeldung.hexa.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.baeldung.hexa.domain.Book;
import org.baeldung.hexa.domain.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliInputParserImpl implements InputParser {

    private final static Logger LOGGER = LoggerFactory.getLogger(CliInputParserImpl.class);
    private BookService bookService;

    public CliInputParserImpl(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void processUserInput() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while (!(line = reader.readLine()).equals("q")) {
            if (line.equals("c")) {
                consumeBooks();
            }
            if (line.equals("p")) {
                produceBooks();
            }
        }

    }

    private void consumeBooks() {
        List<Book> booksByAuthor = bookService.getBooksByAuthor("Isaac Asimov");
        if (booksByAuthor.isEmpty()) {
            LOGGER.info("There are no books by this author");
        }
        for (Book b : booksByAuthor) {
            LOGGER.info("Found book for {} : {}", b.getAuthor(), b.getName());
        }
    }

    private void produceBooks() {
        Book book1 = new Book("Foundation", "Isaac Asimov");
        Book book2 = new Book("The man in the high castle", "Philip K. Dick");
        Book book3 = new Book("Norwegian wood", "Haruki Murakami");
        bookService.storeBook(book1);
        bookService.storeBook(book2);
        bookService.storeBook(book3);
    }
}

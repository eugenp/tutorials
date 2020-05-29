package com.baeldung.hexagonal.architecture.adapter.input;

import com.baeldung.hexagonal.architecture.domain.Book;
import com.baeldung.hexagonal.architecture.input.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

// adapter
public class CliApi {

    private BookService bookService;

    public static final Logger log = LoggerFactory.getLogger(CliApi.class);

    public CliApi(BookService bookService) {
        this.bookService = bookService;
    }

    public void createBook() {
        // Get all inputs from scanner
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter id:");
        String id = scanner.nextLine();
        System.out.println("Enter title:");
        String title = scanner.nextLine();
        System.out.println("Enter author:");
        String author = scanner.nextLine();

        // Create a new book
        Book book = new Book("1", "title", "author");
        bookService.createBook(book);
        log.info("Created new book");
    }

    public void listAllBooks() {
        List<Book> allBooks = bookService.getAllBooks();

        allBooks.forEach(book -> {
            System.out.println(book.toString());
            log.info("Book: " + book.toString());
        });
    }


}

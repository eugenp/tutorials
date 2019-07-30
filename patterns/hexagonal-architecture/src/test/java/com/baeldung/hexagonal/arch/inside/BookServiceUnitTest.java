package com.baeldung.hexagonal.arch.inside;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class BookServiceUnitTest {

    @Test
    public void givenBookWithoutAsterisk_thenBookSavedWithAsterisk() {
        Bookstore bookstore = new Bookstore() {
            private List<Book> books = new ArrayList<>();

            @Override
            public void save(String title) {
                String newTitle = title.endsWith("*") ? title : title.concat("*");
                Book book = new Book();
                book.setTitle(newTitle);
                books.add(book);
            }

            @Override
            public List<Book> getBooks() {
                return Collections.unmodifiableList(books);
            }

        };
        String bookTitle = "Book";
        BookService bookService = new BookService(bookstore);
        bookService.save(bookTitle);

        List<Book> books = bookService.getBooks();

        assertEquals(1, books.size());
        assertEquals("Book*", books.get(0)
            .getTitle());
    }

    @Test
    public void givenBookWithAsterisk_thenBookSavedAsIs() {
        Bookstore bookstore = new Bookstore() {
            private List<Book> books = new ArrayList<>();

            @Override
            public void save(String title) {
                String newTitle = title.endsWith("*") ? title : title.concat("*");
                Book book = new Book();
                book.setTitle(newTitle);
                books.add(book);
            }

            @Override
            public List<Book> getBooks() {
                return Collections.unmodifiableList(books);
            }

        };
        String bookTitle = "Book***";
        BookService bookService = new BookService(bookstore);
        bookService.save(bookTitle);

        List<Book> books = bookService.getBooks();

        assertEquals(1, books.size());
        assertEquals("Book***", books.get(0)
            .getTitle());
    }

}

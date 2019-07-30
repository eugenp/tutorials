package com.baeldung.hexagonal.arch.inside;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookServiceIntegrationTest {

    @Autowired
    private BookService bookService;

    @Test
    public void givenValidBookTitle_thenBookSaved() {
        String bookTitle = "Book Title";
        bookService.save(bookTitle);

        List<Book> books = bookService.getBooks();

        assertEquals(1, books.size());
        assertEquals("Book Title", books.get(0)
            .getTitle());
    }

    @Test
    public void givenNullBookTitle_thenSaveFails() {
        assertThrows(IllegalArgumentException.class, () -> bookService.save(null));
    }

    @Test
    public void givenEmptyBookTitle_thenSaveFails() {
        assertThrows(IllegalArgumentException.class, () -> bookService.save("   "));
    }
}

package com.baeldung.boot.hexagonal.domain.ports.service;

import com.baeldung.boot.hexagonal.domain.models.Book;
import com.baeldung.boot.hexagonal.domain.ports.out.BookPersistence;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@DisplayName("Domain Unit Tests")
class BookServiceImplUnitTest {

    private static List<Book> books;
    @InjectMocks
    private BookServiceImpl bookService;
    @Mock
    private BookPersistence bookPersistence;

    @BeforeAll
    private static void populateBooks() {
        log.info("Setting up the unit tests for BookService. Populating the books...");
        books = BooksTestUtility.populateBooks();
    }

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Check book creation")
    void whenCalledAddBook_thenVerify() {
        final Book book = books.get(0);

        doNothing().when(bookPersistence).addBook(book);

        bookService.addBook(book);
        verify(bookPersistence, times(1)).addBook(book);
    }

    @Test
    @DisplayName("Check get book")
    void giveBookId_whenCalledGetBook_thenCheck() {
        when(bookPersistence.getBook(1L)).thenReturn(books.get(0));

        Book book = bookService.getBook(1L);

        assertEquals("Spring in Action", book.getTitle());
        verify(bookPersistence, times(1)).getBook(1L);
    }

    @Test
    @DisplayName("Check getting all books")
    void whenCalledGetBooks_thenCheck() {
        when(bookPersistence.getBooks()).thenReturn(books);

        List<Book> booksList = bookService.getBooks();

        assertEquals(3, booksList.size());
        verify(bookPersistence, times(1)).getBooks();
    }

    @Test
    @DisplayName("Check book deletion")
    void givenBook_whenCalledDeleteBook_thenVerify() {
        doNothing().when(bookPersistence).deleteBook(1L);

        bookService.deleteBook(1L);

        verify(bookPersistence, times(1)).deleteBook(1L);
    }

}

package com.baeldung.hecagonal.controller;

import com.baeldung.HexagonalArchitectureJavaApplication;
import com.baeldung.hexagonal.controller.BookController;
import com.baeldung.hexagonal.domain.Book;
import com.baeldung.hexagonal.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = HexagonalArchitectureJavaApplication.class)
class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController objectUnderTest;

    @Test
    void givenNewBook_thenNewBookWillBeAdded() {

        Book newBook = new Book();
        newBook.setName("Spring Tutorial");
        when(bookService.addBook(newBook)).thenReturn(1);

        objectUnderTest.addBook(newBook);

        verify(bookService).addBook(newBook);
    }

    @Test
    void whenGettingAllBooks_thenListOfBooksWillReturned() {

        List<Book> books = new ArrayList<>();
        Book firstBook = new Book();
        firstBook.setName("Spring Tutorial");
        Book secondBook = new Book();
        secondBook.setName("Java Tutorial");
        books.add(firstBook);
        books.add(secondBook);
        when(bookService.getAllBooks()).thenReturn(books);
        ResponseEntity responseEntity = objectUnderTest.getAllBooks();

        assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).toString().contains("Java Tutorial"));
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).toString().contains("Spring Tutorial"));
    }

    @Test
    void givenName_thenBookWithThatNameWillBeReturned() {

        Book book = new Book();
        book.setName("Java Tutorial");
        when(bookService.findBookByName("Java Tutorial")).thenReturn(Optional.of(Collections.singletonList(book)));
        ResponseEntity responseEntity = objectUnderTest.findBookByName("Java Tutorial");

        assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).toString().contains("Java Tutorial"));
    }

    @Test
    void givenName_thenBookWithThatNameNotFound() {

        when(bookService.findBookByName("Java Tutorial")).thenReturn(Optional.empty());
        ResponseEntity responseEntity = objectUnderTest.findBookByName("Java Tutorial");

        assertTrue(responseEntity.getStatusCode() == HttpStatus.NOT_FOUND);
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).toString().contains("Book with name Java Tutorial was not found"));
    }
}
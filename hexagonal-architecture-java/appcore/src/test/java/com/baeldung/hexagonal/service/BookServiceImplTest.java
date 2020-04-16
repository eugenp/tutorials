package com.baeldung.hexagonal.service;

import com.baeldung.HexagonalArchitectureJavaApplication;
import com.baeldung.hexagonal.domain.Book;
import com.baeldung.hexagonal.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = HexagonalArchitectureJavaApplication.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl objectUnderTest;

    @Test
    void givenNewBook_thenNewBookWillBeAdded() {

        Book newBook = new Book();
        newBook.setName("Spring Tutorial");
        when(bookRepository.addBook(newBook)).thenReturn(1);
        int newBookId = objectUnderTest.addBook(newBook);

        assertEquals(newBookId, 1);
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
        when(bookRepository.getAllBooks()).thenReturn(books);
        List<Book> bookList = objectUnderTest.getAllBooks();

        assertNotNull(bookList);
        assertEquals(bookList.size(), 2);
    }

    @Test
    void givenBookName_thenBookWithThatNameWillBeReturned() {

        Book book = new Book();
        book.setName("Java Tutorial");
        List<Book> foundBooks = Collections.singletonList(book);
        when(bookRepository.findBookByName("Java Tutorial")).thenReturn(Optional.of(foundBooks));
        Optional<List<Book>> foundBook = objectUnderTest.findBookByName("Java Tutorial");

        assertTrue(foundBook.isPresent());
        assertEquals(foundBook.get().size(), 1);
    }

}
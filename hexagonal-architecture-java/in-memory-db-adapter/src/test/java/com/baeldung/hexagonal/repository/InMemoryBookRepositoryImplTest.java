package com.baeldung.hexagonal.repository;

import com.baeldung.HexagonalArchitectureJavaApplication;
import com.baeldung.hexagonal.domain.Book;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;


@SpringBootTest(classes = HexagonalArchitectureJavaApplication.class)
class InMemoryBookRepositoryImplTest {

    @Autowired
    private InMemoryBookRepositoryImpl objectUnderTest;

    @Before
    void setup() {
        objectUnderTest = new InMemoryBookRepositoryImpl();
    }

    @Test
    void givenNewBook_thenNewBookWillBeAdded() {

        Book newBook = new Book();
        newBook.setName("Spring Tutorial");
        int newBookId = objectUnderTest.addBook(newBook);

        assertTrue(newBookId >= 0);
    }

    @Test
    void whenGettingAllBooks_thenListOfBooksWillReturned() {

        Book firstBook = new Book();
        firstBook.setName("Spring Tutorial");
        Book secondBook = new Book();
        secondBook.setName("Java Tutorial");
        objectUnderTest.addBook(firstBook);
        objectUnderTest.addBook(secondBook);
        List<Book> bookList = objectUnderTest.getAllBooks();

        assertNotNull(bookList);
        assertTrue(bookList.size() >= 2);
        assertTrue(bookList.contains(firstBook));
        assertTrue(bookList.contains(secondBook));
    }

    @Test
    void givenName_thenBookWithThatNameWillBeReturned() {

        Book firstBook = new Book();
        firstBook.setName("Spring Tutorial");
        Book secondBook = new Book();
        secondBook.setName("Java Tutorial");
        objectUnderTest.addBook(firstBook);
        objectUnderTest.addBook(secondBook);
        Optional<List<Book>> foundBook = objectUnderTest.findBookByName("Java Tutorial");

        assertTrue(foundBook.isPresent());
        assertEquals(foundBook.get().size(), 1);
    }

}
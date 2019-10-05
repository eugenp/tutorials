package com.baeldung.hexagonal.service;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.hexagonal.HexagonalApplication;
import com.baeldung.hexagonal.domain.Book;
import com.baeldung.hexagonal.repository.BookEntity;
import com.baeldung.hexagonal.repository.BookRepository;

import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase
@SpringBootTest(classes = HexagonalApplication.class)
public class BookServiceIntegrationTest extends TestCase {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository repository;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @After
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    public void whenAddingValidBook_ThenSuccess() {
        // assign - given

        // act - when
        bookService.addBook("java", "abc", 100);

        // assert
        // test completed without any exception
    }

    @Test
    public void givenBookNameMissing_whenAddingBook_ThenGotRuntimeException() {
        // assign - given
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Missing mandatory field 'name'");

        // act - when
        bookService.addBook(null, "abc", 100);

    }

    @Test
    public void givenBookAuthorMissing_whenAddingBook_ThenGotRuntimeException() {
        // assign - given
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Missing mandatory field 'author'");

        // act - when
        bookService.addBook("java", null, 100);

    }

    @Test
    public void givenBookPriceNegative_whenAddingBook_ThenGotRuntimeException() {
        // assign - given
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Price of book must be bigger than 0");

        // act - when
        bookService.addBook("java", "abc", -100);

    }

    @Test
    public void givenBookExist_whenSearchingBook_ThenGotSuccess() {
        // assign - given
        createTestBook("java", "abc", 100);
        createTestBook("c++", "xyz", 200);
        Book book = new Book("java", "abc", 100);

        // act - when
        Book response = bookService.search("java");

        // assert - then
        assertEquals(book, response);
    }

    @Test
    public void givenBookDoesntExist_whenSearchingBook_ThenNullResponse() {
        // assign - given
        createTestBook("java", "abc", 100);
        createTestBook("c++", "xyz", 200);

        // act - when
        Book response = bookService.search("python");

        // assert - then
        assertNull(response);
    }

    private void createTestBook(String name, String author, int price) {
        BookEntity book = new BookEntity(name, author, price);
        repository.saveAndFlush(book);
    }
}
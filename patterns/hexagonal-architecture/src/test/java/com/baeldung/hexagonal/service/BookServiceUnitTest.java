package com.baeldung.hexagonal.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.hexagonal.HexagonalApplication;
import com.baeldung.hexagonal.domain.Book;
import com.baeldung.hexagonal.port.outbound.BookOutboundPort;

import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@ActiveProfiles("test-book-service")
@SpringBootTest(classes = HexagonalApplication.class)
public class BookServiceUnitTest extends TestCase {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookOutboundPort customerOutboundPort;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void whenAddingValidBook_ThenSuccess() {
        // assign - given
        doNothing().when(customerOutboundPort)
            .addBook(any(Book.class));

        // act - when
        bookService.addBook("java", "abc", 100);

        // assert - then
        verify(customerOutboundPort, timeout(1)).addBook(any(Book.class));
    }

    @Test
    public void givenBookNameMissing_whenAddingBook_ThenGotRuntimeException() {
        // assign - given
        doNothing().when(customerOutboundPort)
            .addBook(any(Book.class));
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Missing mandatory field 'name'");

        // act - when
        bookService.addBook(null, "abc", 100);

        // assert - then
        verify(customerOutboundPort, timeout(1)).addBook(any(Book.class));
    }

    @Test
    public void givenBookAuthorMissing_whenAddingBook_ThenGotRuntimeException() {
        // assign - given
        doNothing().when(customerOutboundPort)
            .addBook(any(Book.class));
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Missing mandatory field 'author'");

        // act - when
        bookService.addBook("java", null, 100);
        // assert - then
        verify(customerOutboundPort, timeout(1)).addBook(any(Book.class));
    }

    @Test
    public void givenBookPriceNegative_whenAddingBook_ThenGotRuntimeException() {
        // assign - given
        doNothing().when(customerOutboundPort)
            .addBook(any(Book.class));
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Price of book must be bigger than 0");

        // act - when
        bookService.addBook("java", "abc", -100);

        // assert - then
        verify(customerOutboundPort, timeout(1)).addBook(any(Book.class));
    }

    @Test
    public void givenBookExist_whenSearchingBook_ThenGotSuccess() {
        // assign - given
        Book book = new Book("java", "abc", 100);
        when(customerOutboundPort.getBook(anyString())).thenReturn(book);

        // act - when
        Book response = bookService.search("java");

        // assert - then
        assertEquals(book, response);
        verify(customerOutboundPort, timeout(1)).getBook(anyString());
    }
}

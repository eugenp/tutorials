package com.baeldung.testng.softassert;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class SoftAssertionUnitTest {

    private BookService bookService;

    @BeforeMethod
    void setup() {
        bookService = mock(BookService.class);
        Book book = new Book();
        when(bookService.getBook(any()))
            .thenReturn(book);
    }

    @Test
    void givenBookWithNullFields_whenUsingMultipleAssertions_thenUseSoftAssert() {
        Book gatsby = bookService.getBook("9780743273565");

        // Traditional assertions - uncomment to see the test fail, only running the first assertion
        // assertNotNull(gatsby.isbn, "ISBN");
        // assertNotNull(gatsby.title, "title");
        // assertNotNull(gatsby.author, "author");

        // SoftAssert - uncomment to see the test fail, but with all assertions run
        // SoftAssert softAssert = new SoftAssert();
        // softAssert.assertNotNull(gatsby.isbn, "ISBN");
        // softAssert.assertNotNull(gatsby.title, "title");
        // softAssert.assertNotNull(gatsby.author, "author");
        // softAssert.assertAll();
    }
}

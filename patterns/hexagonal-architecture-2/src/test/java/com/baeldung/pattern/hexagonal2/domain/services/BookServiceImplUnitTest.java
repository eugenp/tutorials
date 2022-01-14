package com.baeldung.pattern.hexagonal2.domain.services;

import com.baeldung.pattern.hexagonal2.domain.model.Book;
import com.baeldung.pattern.hexagonal2.domain.port.BookServicePort;
import com.baeldung.pattern.hexagonal2.domain.services.impl.BookServiceImpl;
import org.junit.Rule;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceImplUnitTest {

    @Mock
    private BookServicePort port;

    @InjectMocks
    private BookServiceImpl testService;

    @Rule //initMocks
    public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void givenValidISBN_whenCalledGetBook_thenBooksReturns() {
        final String title = "FAKE TITLE";
        when(port.getBookTitle(any(String.class))).thenReturn(title);
        final String isbn = "12345678901";
        Book testResponse = testService.getBook(isbn);
        Assertions.assertNotNull(testResponse);
        Assertions.assertEquals(isbn, testResponse.getIsbn());
        Assertions.assertEquals(title, testResponse.getTitle());
    }

    @Test
    public void givenInvalidISBN_whenCalledGetBook_thenThrowsIllegalArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            final String isbn = "1234567";
            testService.getBook(isbn);
        });
    }

}
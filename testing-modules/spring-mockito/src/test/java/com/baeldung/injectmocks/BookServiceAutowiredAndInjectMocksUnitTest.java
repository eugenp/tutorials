package com.baeldung.injectmocks;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookServiceAutowiredAndInjectMocksUnitTest {

    @Mock
    private DatabaseService databaseService;

    @Autowired
    @InjectMocks
    private BookService bookService;

    @Test
    void givenBookService_whenGettingBook_thenBookIsCorrect() throws JsonProcessingException {
        Book book1 = new Book("1234", "Inferno", "Dan Brown");

        MockitoAnnotations.openMocks(this);

        when(databaseService.findById(eq("1234"))).thenReturn(book1);
        String bookString1 = bookService.getBook("1234");
        Assertions.assertTrue(bookString1.contains("Dan Brown"));
    }
}
package com.baeldung.injectmocks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookServiceMockAndInjectMocksUnitTest {

    @Mock
    private DatabaseService databaseService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private BookService bookService;

    @Test
    void givenBookService_whenGettingBook_thenBookIsCorrect() throws JsonProcessingException {
        Book book1 = new Book("1234", "Inferno", "Dan Brown");
        when(databaseService.findById(eq("1234"))).thenReturn(book1);

        when(objectMapper.writeValueAsString(any())).thenReturn(new ObjectMapper().writeValueAsString(book1));

        String bookString1 = bookService.getBook("1234");
        Assertions.assertTrue(bookString1.contains("Dan Brown"));
    }

}
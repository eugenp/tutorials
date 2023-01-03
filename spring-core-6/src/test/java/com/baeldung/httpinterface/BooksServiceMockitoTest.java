package com.baeldung.httpinterface;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BooksServiceMockitoTest {

    private static final String SERVICE_URL = "https://www.baeldung.com/demo";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private BooksService booksService;

    @Test
    void givenMockedService_whenAllBooksAreRequested_thenTwoBooksAreReturned() throws JsonProcessingException {
        when(booksService.getBooks())
          .thenReturn(objectMapper.readerForListOf(Book.class)
            .readValue("[{\"title\":\"Book_1\",\"author\":\"Author_1\",\"year\":1998},{\"title\":\"Book_2\",\"author\":\"Author_2\",\"year\":1999}]"));

        List<Book> books = booksService.getBooks();
        assertEquals(2, books.size());
    }

    @Test
    void givenMockedService_whenBookByTitleIsRequest_thenCorrectBookIsReturned() throws JsonProcessingException {
        when(booksService.getBook("Book_1"))
          .thenReturn(objectMapper.readerFor(Book.class)
            .readValue("{\"title\":\"Book_1\",\"author\":\"Author_1\",\"year\":1998}"));

        Book book = booksService.getBook("Book_1");
        assertEquals("Book_1", book.title());
    }

}

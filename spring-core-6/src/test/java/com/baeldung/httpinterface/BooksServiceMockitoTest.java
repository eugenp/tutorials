package com.baeldung.httpinterface;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BooksServiceMockitoTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestBodySpec requestBody;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUri;

    @Mock
    private WebClient.ResponseSpec response;

    @InjectMocks
    private BooksClient booksClient;

    @Test
    void givenMockedWebClient_whenAllBooksAreRequested_thenTwoBooksAreReturned() throws JsonProcessingException {
        BooksService booksService = booksClient.getBooksService();
        when(webClient.method(HttpMethod.GET)).thenReturn(requestBodyUri);
        when(requestBodyUri.uri(anyString(), anyMap())).thenReturn(requestBody);
        when(requestBody.retrieve()).thenReturn(response);
        when(response.bodyToMono(new ParameterizedTypeReference<List<Book>>(){}))
          .thenReturn(Mono.just(List.of(
            new Book("Book_1", "Author_1", 1998),
            new Book("Book_2", "Author_2", 1999)
          )));

        List<Book> books = booksService.getBooks();
        assertEquals(2, books.size());
    }

    @Test
    void givenMockedWebClient_whenBookByTitleIsRequest_thenCorrectBookIsReturned() throws JsonProcessingException {
        BooksService booksService = booksClient.getBooksService();
        when(webClient.method(HttpMethod.GET)).thenReturn(requestBodyUri);
        when(requestBodyUri.uri(anyString(), anyMap())).thenReturn(requestBody);
        when(requestBody.retrieve()).thenReturn(response);
        when(response.bodyToMono(new ParameterizedTypeReference<Book>(){}))
          .thenReturn(Mono.just(new Book("Book_1", "Author_1", 1998)));

        Book book = booksService.getBook("Book_1");
        assertEquals("Book_1", book.title());
    }

    @Test
    void givenMockedWebClient_whenSaveNewBookIsRequest_thenCorrectStatusCodeIsReturned() {
        BooksService booksService = booksClient.getBooksService();
        when(webClient.method(HttpMethod.POST)).thenReturn(requestBodyUri);
        when(requestBodyUri.uri(anyString(), anyMap())).thenReturn(requestBody);
        when(requestBody.retrieve()).thenReturn(response);
        when(response.bodyToMono(new ParameterizedTypeReference<Book>(){}))
          .thenReturn(Mono.just(new Book("Book_3", "Author_3", 2000)));

        ResponseEntity<Book> response = booksService.saveBook(new Book("Book_3", "Author_3", 2000));
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

}

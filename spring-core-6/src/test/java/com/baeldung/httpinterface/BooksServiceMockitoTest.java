package com.baeldung.httpinterface;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
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

    @Mock
    private Mono<ResponseEntity<Void>> monoResponseEntity;

    @Mock
    private ResponseEntity<Void> responseEntity;

    @InjectMocks
    private BooksClient booksClient;

    @Test
    void givenMockedWebClient_whenAllBooksAreRequested_thenTwoBooksAreReturned() {
        BooksService booksService = booksClient.getBooksService();
        when(webClient.method(HttpMethod.GET)).thenReturn(requestBodyUri);
        when(requestBodyUri.uri(anyString(), anyMap())).thenReturn(requestBody);
        when(requestBody.retrieve()).thenReturn(response);
        when(response.bodyToMono(new ParameterizedTypeReference<List<Book>>(){}))
          .thenReturn(Mono.just(List.of(
            new Book(1,"Book_1", "Author_1", 1998),
            new Book(2, "Book_2", "Author_2", 1999)
          )));

        List<Book> books = booksService.getBooks();
        assertEquals(2, books.size());
    }

    @Test
    void givenMockedWebClient_whenBookByIdIsRequested_thenCorrectBookIsReturned() {
        BooksService booksService = booksClient.getBooksService();
        when(webClient.method(HttpMethod.GET)).thenReturn(requestBodyUri);
        when(requestBodyUri.uri(anyString(), anyMap())).thenReturn(requestBody);
        when(requestBody.retrieve()).thenReturn(response);
        when(response.bodyToMono(new ParameterizedTypeReference<Book>(){}))
          .thenReturn(Mono.just(new Book(1,"Book_1", "Author_1", 1998)));

        Book book = booksService.getBook(1);
        assertEquals("Book_1", book.title());
    }

    @Test
    void givenMockedWebClient_whenSaveNewBookIsRequested_thenCorrectBookIsReturned() {
        BooksService booksService = booksClient.getBooksService();
        when(webClient.method(HttpMethod.POST)).thenReturn(requestBodyUri);
        when(requestBodyUri.uri(anyString(), anyMap())).thenReturn(requestBody);
        when(requestBody.retrieve()).thenReturn(response);
        when(response.bodyToMono(new ParameterizedTypeReference<Book>(){}))
          .thenReturn(Mono.just(new Book(3, "Book_3", "Author_3", 2000)));

        Book book = booksService.saveBook(new Book(3, "Book_3", "Author_3", 2000));
        assertEquals("Book_3", book.title());
    }

    @Test
    void givenMockedWebClient_whenDeleteBookIsRequested_thenCorrectCodeIsReturned() {
        BooksService booksService = booksClient.getBooksService();
        when(webClient.method(HttpMethod.DELETE)).thenReturn(requestBodyUri);
        when(requestBodyUri.uri(anyString(), anyMap())).thenReturn(requestBody);
        when(requestBody.retrieve()).thenReturn(response);
        when(response.toBodilessEntity()).thenReturn(monoResponseEntity);
        when(monoResponseEntity.block(any())).thenReturn(responseEntity);
        when(responseEntity.getStatusCode()).thenReturn(HttpStatusCode.valueOf(200));

        ResponseEntity<Void> response = booksService.deleteBook(3);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

}

package com.baeldung.httpinterface;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.mockito.BDDMockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BooksServiceMockitoTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private WebClient webClient;

    @InjectMocks
    private BooksClient booksClient;

    @Test
    void givenMockedWebClientReturnsTwoBooks_whenGetBooksServiceMethodIsCalled_thenListOfTwoBooksIsReturned() {
        given(webClient.method(HttpMethod.GET)
          .uri(anyString(), anyMap())
          .retrieve()
          .bodyToMono(new ParameterizedTypeReference<List<Book>>(){}))
          .willReturn(Mono.just(List.of(
            new Book(1,"Book_1", "Author_1", 1998),
            new Book(2, "Book_2", "Author_2", 1999)
          )));

        BooksService booksService = booksClient.getBooksService();
        List<Book> books = booksService.getBooks();
        assertEquals(2, books.size());
    }

    @Test
    void givenMockedWebClientReturnsBook_whenGetBookServiceMethodIsCalled_thenBookIsReturned() {
        given(webClient.method(HttpMethod.GET)
          .uri(anyString(), anyMap())
          .retrieve()
          .bodyToMono(new ParameterizedTypeReference<Book>(){}))
          .willReturn(Mono.just(new Book(1,"Book_1", "Author_1", 1998)));

        BooksService booksService = booksClient.getBooksService();
        Book book = booksService.getBook(1);
        assertEquals("Book_1", book.title());
    }

    @Test
    void givenMockedWebClientReturnsBook_whenSaveBookServiceMethodIsCalled_thenBookIsReturned() {
        given(webClient.method(HttpMethod.POST)
          .uri(anyString(), anyMap())
          .retrieve()
          .bodyToMono(new ParameterizedTypeReference<Book>(){}))
          .willReturn(Mono.just(new Book(3, "Book_3", "Author_3", 2000)));

        BooksService booksService = booksClient.getBooksService();
        Book book = booksService.saveBook(new Book(3, "Book_3", "Author_3", 2000));
        assertEquals("Book_3", book.title());
    }

    @Test
    void givenMockedWebClientReturnsOk_whenDeleteBookServiceMethodIsCalled_thenOkCodeIsReturned() {
        given(webClient.method(HttpMethod.DELETE)
          .uri(anyString(), anyMap())
          .retrieve()
          .toBodilessEntity()
          .block(any())
          .getStatusCode())
          .willReturn(HttpStatusCode.valueOf(200));

        BooksService booksService = booksClient.getBooksService();
        ResponseEntity<Void> response = booksService.deleteBook(3);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }

}

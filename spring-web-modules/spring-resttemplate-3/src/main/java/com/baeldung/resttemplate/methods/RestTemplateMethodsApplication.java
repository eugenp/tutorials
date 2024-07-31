package com.baeldung.resttemplate.methods;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * Examples of making the same call with RestTemplate
 * using postForEntity(), exchange(), and execute().
 */
@SpringBootApplication
public class RestTemplateMethodsApplication {
    private final static RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {

    }

    private static void postForEntity() {
        Book book = new Book(
            "Cruising Along with Java",
            "Venkat Subramaniam",
            2023);

        ResponseEntity<Book> response = restTemplate.postForEntity(
            "https://api.bookstore.com",
            book,
            Book.class);
    }

    private static void exchange() {
        Book book = new Book(
            "Effective Java",
            "Joshua Bloch",
            2001);

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("username", "password");

        ResponseEntity<Book> response = restTemplate.exchange(
            "https://api.bookstore.com",
            HttpMethod.POST,
            new HttpEntity<>(book, headers),
            Book.class);
    }

    private static void execute() {
        ResponseEntity<Book> response = restTemplate.execute(
            "https://api.bookstore.com",
             HttpMethod.POST,
             new RequestCallback() {
                 @Override
                 public void doWithRequest(ClientHttpRequest request) throws IOException {
                        // Create or decorate the request object as needed
                 }
             },
             new ResponseExtractor<ResponseEntity<Book>>() {
                 @Override
                 public ResponseEntity<Book> extractData(ClientHttpResponse response) throws IOException {
                     // extract required data from response
                     return null;
                 }
            }
        );

        // Could also use some factory methods in RestTemplate for
        // the request callback and/or response extractor

        Book book = new Book(
            "Reactive Spring",
            "Josh Long",
            2020);

        response = restTemplate.execute(
            "https://api.bookstore.com",
            HttpMethod.POST,
            restTemplate.httpEntityCallback(book),
            restTemplate.responseEntityExtractor(Book.class)
        );
    }

    private static class Book {
        String title;
        String author;
        int yearPublished;

        public Book(String title, String author, int yearPublished) {
            this.title = title;
            this.author = author;
            this.yearPublished = yearPublished;
        }
    }
}

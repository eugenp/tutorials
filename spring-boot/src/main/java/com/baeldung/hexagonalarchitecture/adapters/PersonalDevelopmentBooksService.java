package com.baeldung.hexagonalarchitecture.adapters;

import com.baeldung.hexagonalarchitecture.contract.BooksService;
import com.baeldung.hexagonalarchitecture.model.Book;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class PersonalDevelopmentBooksService implements BooksService {

    private RestTemplate restTemplate;

    public PersonalDevelopmentBooksService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Book> retrieveBooks() {
        ResponseEntity<List<Book>> response = restTemplate.exchange(
                "http://localhost:9090/springbootapp/books/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Book>>(){});

        return response.getBody();

    }
}

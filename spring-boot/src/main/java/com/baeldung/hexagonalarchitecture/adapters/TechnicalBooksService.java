package com.baeldung.hexagonalarchitecture.adapters;

import com.baeldung.hexagonalarchitecture.contract.BooksService;
import com.baeldung.hexagonalarchitecture.model.Book;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class TechnicalBooksService implements BooksService {

    private RestTemplate restTemplate;

    public TechnicalBooksService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Book> retrieveBooks() {
        ResponseEntity<List<Book>> response = restTemplate.exchange(
                "http://localhost:9090/springbootapp/technicalbooks",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Book>>(){});

        return response.getBody();

    }
}

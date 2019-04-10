package com.baeldung.hexagonalarchitecture.configuration;

import com.baeldung.hexagonalarchitecture.adapters.PersonalDevelopmentBooksService;
import com.baeldung.hexagonalarchitecture.contract.BooksService;
import com.baeldung.hexagonalarchitecture.adapters.TechnicalBooksService;
import com.baeldung.hexagonalarchitecture.rest.BooksController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BookProviderContext {


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public BooksService technicalBooksProvider(RestTemplate restTemplate) {
        return new TechnicalBooksService(restTemplate);
    }

    //TODO: use this bean when switching the implementation
   /* @Bean
    public BooksService personalDevelopmentBooksProvider(RestTemplate restTemplate) {
        return new PersonalDevelopmentBooksService(restTemplate);
    }*/

    @Bean
    public BooksController booksController(BooksService booksService) {
        return new BooksController(booksService);
    }
}

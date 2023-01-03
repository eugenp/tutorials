package com.baeldung.httpinterface;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Component
public class BooksClient {

    private final BooksService booksService;

    public BooksClient(String baseUrl) {
        WebClient client = WebClient.builder()
          .baseUrl(baseUrl)
          .build();
        HttpServiceProxyFactory httpServiceProxyFactory =
          HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client))
            .build();
        booksService = httpServiceProxyFactory.createClient(BooksService.class);
    }

    public BooksService getBooksService() {
        return booksService;
    }
}

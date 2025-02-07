package com.baeldung.requesttimeout;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

import com.baeldung.requesttimeout.domain.Book;
import com.baeldung.requesttimeout.domain.BookRepository;

import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@RestController
public class RequestTimeoutRestController {

    private final BookRepository bookRepository;
    private final WebClient webClient;
    private final RestClient restClient;

    public RequestTimeoutRestController(BookRepository bookRepository, WebClient webClient, RestClient restClient) {
        this.bookRepository = bookRepository;
        this.webClient = webClient;
        this.restClient = restClient;
    }

    @GetMapping("/author/transactional")
    @Transactional(timeout = 1)
    public String getWithTransactionTimeout(@RequestParam String title) {
        return getAuthor(title);
    }

    private final TimeLimiter ourTimeLimiter = TimeLimiter.of(TimeLimiterConfig.custom()
        .timeoutDuration(Duration.ofMillis(500))
        .build());

    @GetMapping("/author/resilience4j")
    public Callable<String> getWithResilience4jTimeLimiter(@RequestParam String title) {
        return TimeLimiter.decorateFutureSupplier(ourTimeLimiter, () -> CompletableFuture.supplyAsync(() -> getAuthor(title)));
    }

    @GetMapping("/author/mvc-request-timeout")
    public Callable<String> getWithMvcRequestTimeout(@RequestParam String title) {
        return () -> getAuthor(title);
    }

    @GetMapping("/author/webclient")
    public String getWithWebClient(@RequestParam String title) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder.path("/author/transactional")
                .queryParam("title", title)
                .build())
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }

    @GetMapping("/author/restclient")
    public String getWithRestClient(@RequestParam String title) {
        return restClient.get()
            .uri(uriBuilder -> uriBuilder.path("/author/transactional")
                .queryParam("title", title)
                .build())
            .retrieve()
            .body(String.class);
    }

    private String getAuthor(String title) {
        bookRepository.wasteTime();
        return bookRepository.findById(title)
            .map(Book::getAuthor)
            .orElse("No book found for this title.");
    }
}

package com.baeldung.spring.retry;

import java.time.Duration;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Component
@AllArgsConstructor
public class ExternalConnector {

    private static final String PATH_BY_ID = "/data/{id}";

    private final WebClient webClient;

    public Mono<String> getData(String stockId) {
        return webClient.get()
            .uri(PATH_BY_ID, stockId)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new ServiceException("Server error", response.rawStatusCode())))
            .bodyToMono(String.class)
            .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                .filter(throwable -> throwable instanceof ServiceException)
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                    throw new ServiceException("External Service failed to process after max retries", HttpStatus.SERVICE_UNAVAILABLE.value());
                }));
    }

    public Mono<String> getDataWithRetry(String stockId) {
        return webClient.get()
            .uri(PATH_BY_ID, stockId)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(String.class)
            .retryWhen(Retry.max(3));
    }

    public Mono<String> getDataWithRetryFixedDelay(String stockId) {
        return webClient.get()
            .uri(PATH_BY_ID, stockId)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(String.class)
            .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)));
    }

    public Mono<String> getDataWithRetryBackoff(String stockId) {
        return webClient.get()
            .uri(PATH_BY_ID, stockId)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(String.class)
            .retryWhen(Retry.backoff(3, Duration.ofSeconds(2)));
    }

    public Mono<String> getDataWithRetryBackoffJitter(String stockId) {
        return webClient.get()
            .uri(PATH_BY_ID, stockId)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(String.class)
            .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                .jitter(1));
    }

}

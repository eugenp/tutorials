package com.baeldung.webclient.status;

import com.baeldung.webclient.status.exception.BadRequestException;
import com.baeldung.webclient.status.exception.ServerErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class WebClientStatusCodeHandler {

    public static Mono<String> getResponseBodyUsingExchangeFilterFunction(String uri) {
        ExchangeFilterFunction errorResponseFilter = ExchangeFilterFunction
            .ofResponseProcessor(WebClientStatusCodeHandler::exchangeFilterResponseProcessor);
        return WebClient
            .builder()
            .filter(errorResponseFilter)
            .build()
            .post()
            .uri(uri)
            .retrieve()
            .bodyToMono(String.class);
    }

    public static Mono<String> getResponseBodyUsingOnStatus(String uri) {
        return WebClient
            .builder()
            .build()
            .post()
            .uri(uri)
            .retrieve()
            .onStatus(
                HttpStatus.INTERNAL_SERVER_ERROR::equals,
                response -> response.bodyToMono(String.class).map(ServerErrorException::new))
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> response.bodyToMono(String.class).map(BadRequestException::new))
            .bodyToMono(String.class);
    }

    private static Mono<ClientResponse> exchangeFilterResponseProcessor(ClientResponse response) {
        HttpStatus status = response.statusCode();
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            return response.bodyToMono(String.class)
                .flatMap(body -> Mono.error(new ServerErrorException(body)));
        }
        if (HttpStatus.BAD_REQUEST.equals(status)) {
            return response.bodyToMono(String.class)
                .flatMap(body -> Mono.error(new BadRequestException(body)));
        }
        return Mono.just(response);
    }
}

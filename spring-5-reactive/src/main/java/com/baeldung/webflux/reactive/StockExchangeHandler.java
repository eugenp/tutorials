package com.baeldung.webflux.reactive;

import com.baeldung.webflux.reactive.repository.StockExchange;
import com.baeldung.webflux.reactive.model.StockQuote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class StockExchangeHandler {

    @Autowired
    StockExchange stockExchange;

    public Mono<ServerResponse> lastTrade(ServerRequest serverRequest){
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(stockExchange.trading(), StockQuote.class);
    }

}

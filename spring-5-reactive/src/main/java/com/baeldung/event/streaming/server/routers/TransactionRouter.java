package com.baeldung.event.streaming.server.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.baeldung.event.streaming.server.handlers.TransactionHandler;

@Component
public class TransactionRouter {

    @Bean
    public RouterFunction<ServerResponse> transactionRoute(TransactionHandler transactionHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/transactions")
            .and(RequestPredicates.accept(MediaType.APPLICATION_STREAM_JSON)), transactionHandler::transactionGenerated);
    }
}

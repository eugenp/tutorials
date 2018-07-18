package com.baeldung.webflux.reactive;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;

@Component
public class StockExchangeRouter {

    @Bean
    public RouterFunction<ServerResponse> route(StockExchangeHandler handler){
        return RouterFunctions
                .route(RequestPredicates.GET("/trading")
                .and(RequestPredicates.accept(MediaType.APPLICATION_STREAM_JSON)), handler::lastTrade);
    }
}

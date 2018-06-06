package com.baeldung.reactive.functional.router;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import com.baeldung.reactive.functional.handler.HeartBeatHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

@Component
public class HeartBeatRouter {

    @Bean
    public RouterFunction<?> route(HeartBeatHandler heartBeatHandler) {
        return RouterFunctions
                .route(GET("/heartBeat")
                                .and(accept(TEXT_EVENT_STREAM)),
                        heartBeatHandler::stillAlive);
    }
}
package com.baeldung.reactive.router;

import com.baeldung.reactive.handler.EmailHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class EmailRouter {

    @Bean
    public RouterFunction<ServerResponse> route(EmailHandler handler) {
        return RouterFunctions.route(RequestPredicates
            .GET("/emails")
            .and(RequestPredicates.accept(MediaType.APPLICATION_STREAM_JSON)), 
            handler::getEmails);
    }
}

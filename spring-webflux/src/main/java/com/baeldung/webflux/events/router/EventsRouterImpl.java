
package com.baeldung.webflux.events.router;

import com.baeldung.webflux.events.handler.EventsHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


@Component
public class EventsRouterImpl implements EventsRouter<ServerResponse>{ 

    @Override
    @Bean
    public RouterFunction<ServerResponse> routeEvent(EventsHandler<ServerResponse> handler) {
                return RouterFunctions
                .route(RequestPredicates.GET("/events")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_STREAM_JSON)),
                        handler::handleEvent);
    }


}

package com.baeldung.webflux.events.router;

import com.baeldung.webflux.events.handler.EventsHandler;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@FunctionalInterface
public interface EventsRouter<T extends ServerResponse> {

    RouterFunction<T> routeEvent(EventsHandler<T> handler);
}

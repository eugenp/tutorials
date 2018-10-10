package com.baeldung.hexagon.outbound.gateway.impl;

import com.baeldung.hexagon.core.UserCreatedEvent;
import com.baeldung.hexagon.outbound.gateway.UserEventGateway;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author Ali Dehghani
 */
@Component
public class AppContextUserEventGateway implements UserEventGateway {

    private final ApplicationContext context;

    public AppContextUserEventGateway(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void send(UserCreatedEvent userCreatedEvent) {
        context.publishEvent(userCreatedEvent);
    }
}

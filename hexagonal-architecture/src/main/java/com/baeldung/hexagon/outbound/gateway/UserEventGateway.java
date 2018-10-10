package com.baeldung.hexagon.outbound.gateway;

import com.baeldung.hexagon.core.UserCreatedEvent;

/**
 * @author Ali Dehghani
 */
public interface UserEventGateway {

    void send(UserCreatedEvent userCreatedEvent);
}

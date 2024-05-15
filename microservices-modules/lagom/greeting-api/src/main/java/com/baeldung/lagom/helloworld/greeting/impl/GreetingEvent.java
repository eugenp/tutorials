package com.baeldung.lagom.helloworld.greeting.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.lightbend.lagom.serialization.Jsonable;


public interface GreetingEvent extends Jsonable {

    class ReceivedGreetingEvent implements GreetingEvent {
        private final String fromUser;

        @JsonCreator
        public ReceivedGreetingEvent(String fromUser) {
            this.fromUser = fromUser;
        }

        public String getFromUser() {
            return fromUser;
        }
        
    }

}

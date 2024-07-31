package com.baeldung.lagom.helloworld.greeting.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.CompressedJsonable;
import com.lightbend.lagom.serialization.Jsonable;

public interface GreetingCommand extends Jsonable {

    @SuppressWarnings("serial")
    @JsonDeserialize
    public final class ReceivedGreetingCommand implements GreetingCommand, 
      CompressedJsonable, PersistentEntity.ReplyType<String> {
        private final String fromUser;       

        @JsonCreator
        public ReceivedGreetingCommand(String fromUser) {
            this.fromUser = Preconditions.checkNotNull(fromUser, "fromUser");
        }
        
        public String getFromUser() {
            return fromUser;
        }

    }

}

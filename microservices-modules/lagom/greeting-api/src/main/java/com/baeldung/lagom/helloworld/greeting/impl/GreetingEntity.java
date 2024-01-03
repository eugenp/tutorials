package com.baeldung.lagom.helloworld.greeting.impl;

import java.util.Optional;

import com.baeldung.lagom.helloworld.greeting.impl.GreetingCommand.ReceivedGreetingCommand;
import com.baeldung.lagom.helloworld.greeting.impl.GreetingEvent.ReceivedGreetingEvent;

import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

public class GreetingEntity extends PersistentEntity<GreetingCommand, 
  GreetingEvent, GreetingState> {

    @Override
    public Behavior initialBehavior(Optional<GreetingState> snapshotState) {
        BehaviorBuilder b = newBehaviorBuilder(new GreetingState("Hello "));
        
        b.setCommandHandler(ReceivedGreetingCommand.class,
            (cmd, ctx) -> {
                String fromUser = cmd.getFromUser();
                String currentGreeting = state().getMessage();
                return ctx.thenPersist(
                    new ReceivedGreetingEvent(fromUser),
                    evt -> ctx.reply(currentGreeting + fromUser + "!"));
            });
        
        b.setEventHandler(ReceivedGreetingEvent.class,
            // We simply update the current state
            evt -> state().withMessage("Hello Again "));

        return b.build();
    }
}

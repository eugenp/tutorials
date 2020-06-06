package com.baeldung.akka;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class FirstActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    public static Props props() {
        return Props.create(FirstActor.class);
    }

    @Override
    public void preStart() {
        log.info("Actor started");
    }

    @Override
    public void postStop() {
        log.info("Actor stopped");
    }

    // Messages will not be handled
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .build();
    }
}

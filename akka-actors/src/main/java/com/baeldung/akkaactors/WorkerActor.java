package com.baeldung.akkaactors;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class WorkerActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(Task.class, t -> {

            log.info("Task {} was completed", t.getMessage());

        })
            .matchAny(o -> log.info("received unknown message"))
            .build();
    }
}

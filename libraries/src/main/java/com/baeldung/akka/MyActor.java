package com.baeldung.akka;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class MyActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public void postStop() {
        log.info("Stopping actor {}", this);
    }

    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("printit", p -> {
                    System.out.println("The address of this actor is: " + getSelf());
                    getSender().tell("Got Message", getSelf());
                })
                .build();
    }
}

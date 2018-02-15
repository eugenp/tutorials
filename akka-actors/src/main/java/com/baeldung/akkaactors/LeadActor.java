package com.baeldung.akkaactors;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class LeadActor extends AbstractLoggingActor {

    private final ActorRef worker = getContext().actorOf(Props.create(WorkerActor.class), "worker");

    public static Props props() {
        return Props.create(LeadActor.class);
    }

    public Receive createReceive() {

        return receiveBuilder().match(Task.class, t -> {

            worker.tell(t, getSelf());
            log().info("Task {} was sent to worker", t.getMessage());

        })
            .matchAny(o -> log().info("received unknown message"))
            .build();

    }
}

package com.baeldung.akkaactors;

import org.junit.Test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class RunAkkaActorTest {
    private final ActorSystem system = ActorSystem.create("test-actor-system");

    @Test
    public void givenTask_whenMessageIsSent_shouldReturnProperResponse() {
        Task task = new Task("Buy Cake");
        ActorRef supervisor = system.actorOf(LeadActor.props(), "actor-supervisor");

        supervisor.tell(task, supervisor);

    }

}

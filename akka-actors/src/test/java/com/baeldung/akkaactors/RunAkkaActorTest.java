package com.baeldung.akkaactors;

import com.baeldung.akkaactors.LeadActor;
import com.baeldung.akkaactors.Task;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import org.junit.Test;



public class RunAkkaActorTest {
    private final ActorSystem system = ActorSystem.create("test-actor-system");

    @Test
    public void givenTask_whenMessageIsSent_shouldReturnProperResponse(){
        Task task = new Task("Buy Cake");
        ActorRef supervisor = system.actorOf(LeadActor.props(), "actor-supervisor");

        supervisor.tell(task, supervisor);

    }

}

package com.baeldung.akkahttp;

import java.util.ArrayList;
import java.util.List;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * User Actor
 * 
 */
public class UserActor extends AbstractActor {

    LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    /**
     * In memory List to hold the users added via POST operation.
     */
    private final List<User> users = new ArrayList<>();

    /**
     * To define UserActor Props.
     * 
     * @return Props
     */
    static Props props() {
        return Props.create(UserActor.class);
    }

    /**
     * createReveive() implementation
     */
    @Override
    public Receive createReceive() {
        return receiveBuilder().match(UserRegistryMessages.GetUsers.class, getUsers -> getSender().tell(new Users(users), getSelf()))
            .match(UserRegistryMessages.CreateUser.class, createUser -> {
                users.add(createUser.getUser());
                getSender().tell(new UserRegistryMessages.ActionPerformed(String.format("User %s created.", createUser.getUser()
                    .getName())), getSelf());
            })
            .match(UserRegistryMessages.GetUser.class, getUser -> {
                getSender().tell(users.stream()
                    .filter(user -> user.getName()
                        .equals(getUser.getName()))
                    .findFirst(), getSelf());
            })
            .match(UserRegistryMessages.DeleteUser.class, deleteUser -> {
                users.removeIf(user -> user.getName()
                    .equals(deleteUser.getName()));
                getSender().tell(new UserRegistryMessages.ActionPerformed(String.format("User %s deleted.", deleteUser.getName())), getSelf());

            })
            .matchAny(o -> log.info("received unknown message"))
            .build();
    }

}

package com.baeldung.akkahttp;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import com.baeldung.akkahttp.UserRegistryMessages.ActionPerformed;
import com.baeldung.akkahttp.UserRegistryMessages.CreateUser;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.PathMatchers;
import akka.http.javadsl.server.Route;
import akka.pattern.PatternsCS;
import akka.util.Timeout;
import scala.concurrent.duration.Duration;

public class UserRoutes extends AllDirectives {

    private final ActorRef userActorRef;
    private final LoggingAdapter log;
    Timeout timeout = new Timeout(Duration.create(5, TimeUnit.SECONDS));

    public UserRoutes(ActorSystem system, ActorRef userActorRef) {
        this.userActorRef = userActorRef;
        log = Logging.getLogger(system, this);
    }

    public Route routes() {
        return pathPrefix("users", () -> route(getOrPostUsers(), path(PathMatchers.segment(), name -> route(getUser(name), deleteUser(name)))));
    }

    /**
     * Defines and returns a route to get the user by name
     * 
     * @param name
     * @return Route
     */
    private Route getUser(String name) {

        Route getRoute = get(() -> {
            CompletionStage<Optional<User>> user = PatternsCS.ask(userActorRef, new UserRegistryMessages.GetUser(name), timeout)
                .thenApply(obj -> (Optional<User>) obj);

            return onSuccess(() -> user, performed -> {
                if (performed.isPresent())
                    return complete(StatusCodes.OK, performed.get(), Jackson.marshaller());
                else
                    return complete(StatusCodes.NOT_FOUND);
            });
        });
        return getRoute;
    }

    /**
     * Defines and returns a Route to delete the user by name.
     * 
     * @param name
     * @return Route
     */
    private Route deleteUser(String name) {

        Route deleteRoute = delete(() -> {
            CompletionStage<ActionPerformed> userDeleted = PatternsCS.ask(userActorRef, new UserRegistryMessages.DeleteUser(name), timeout)
                .thenApply(obj -> (ActionPerformed) obj);

            return onSuccess(() -> userDeleted, performed -> {
                log.info("Deleted user [{}]: {}", name, performed.getDescription());
                return complete(StatusCodes.OK, performed, Jackson.marshaller());
            });
        });

        return deleteRoute;
    }

    /**
     * Defines two routes, one to get all users and the other is to post users.
     * 
     * @return
     */
    private Route getOrPostUsers() {
        return pathEnd(() -> route(get(() -> {
            CompletionStage<Users> futureUsers = PatternsCS.ask(userActorRef, new UserRegistryMessages.GetUsers(), timeout)
                .thenApply(obj -> (Users) obj);
            return onSuccess(() -> futureUsers, users -> complete(StatusCodes.OK, users, Jackson.marshaller()));
        }), post(() -> entity(Jackson.unmarshaller(User.class), user -> {
            CompletionStage<ActionPerformed> userCreated = PatternsCS.ask(userActorRef, new CreateUser(user), timeout)
                .thenApply(obj -> (ActionPerformed) obj);
            return onSuccess(() -> userCreated, performed -> {
                log.info("Created user [{}]: {}", user.getName(), performed.getDescription());
                return complete(StatusCodes.CREATED, performed, Jackson.marshaller());
            });
        }))));
    }

}

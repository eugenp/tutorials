package com.baeldung.akkahttp;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import akka.actor.ActorRef;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.pattern.PatternsCS;
import akka.util.Timeout;
import com.baeldung.akkahttp.UserMessages.ActionPerformed;
import com.baeldung.akkahttp.UserMessages.CreateUser;
import scala.concurrent.duration.Duration;
import static akka.http.javadsl.server.PathMatchers.*;

class UserRoutes extends AllDirectives {

  private final ActorRef userActor;

  Timeout timeout = new Timeout(Duration.create(5, TimeUnit.SECONDS));

  UserRoutes(ActorRef userActor) {
    this.userActor = userActor;
  }

  Route routes() {
    return path("users", this::postUser)
            .orElse(path(segment("users").slash(longSegment()), id ->
                    route(getUser(id),
                            deleteUser(id))));
  }

  private Route getUser(Long id) {
    return get(() -> {
      CompletionStage<Optional<User>> user = PatternsCS.ask(userActor, new UserMessages.GetUser(id), timeout)
              .thenApply(obj -> (Optional<User>) obj);

      return onSuccess(() -> user, performed -> {
        if (performed.isPresent())
          return complete(StatusCodes.OK, performed.get(), Jackson.marshaller());
        else
          return complete(StatusCodes.NOT_FOUND);
      });
    });
  }

  private Route deleteUser(Long id) {
    return delete(() -> {
      CompletionStage<ActionPerformed> userDeleted = PatternsCS.ask(userActor, new UserMessages.DeleteUser(id), timeout)
              .thenApply(obj -> (ActionPerformed) obj);

      return onSuccess(() -> userDeleted, performed -> {
        return complete(StatusCodes.OK, performed, Jackson.marshaller());
      });
    });
  }

  private Route postUser() {
    return route(post(() -> entity(Jackson.unmarshaller(User.class), user -> {
      CompletionStage<ActionPerformed> userCreated = PatternsCS.ask(userActor, new CreateUser(user), timeout)
              .thenApply(obj -> (ActionPerformed) obj);

      return onSuccess(() -> userCreated, performed -> {
        return complete(StatusCodes.CREATED, performed, Jackson.marshaller());
      });
    })));
  }

}

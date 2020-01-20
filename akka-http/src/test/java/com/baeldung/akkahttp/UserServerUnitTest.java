package com.baeldung.akkahttp;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.model.HttpEntities;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.testkit.JUnitRouteTest;
import akka.http.javadsl.testkit.TestRoute;
import org.junit.Test;

public class UserServerUnitTest extends JUnitRouteTest {

  ActorSystem system = ActorSystem.create("helloAkkaHttpServer");

  ActorRef userActorRef = system.actorOf(UserActor.props(), "userActor");

  TestRoute appRoute = testRoute(new UserServer(userActorRef).routes());

  @Test
  public void whenRequest_thenActorResponds() {

    appRoute.run(HttpRequest.GET("/users/1"))
            .assertEntity(alice())
            .assertStatusCode(200);

    appRoute.run(HttpRequest.GET("/users/42"))
            .assertStatusCode(404);

    appRoute.run(HttpRequest.DELETE("/users/1"))
            .assertStatusCode(405);

    appRoute.run(HttpRequest.DELETE("/users/42"))
            .assertStatusCode(405);

    appRoute.run(HttpRequest.POST("/users")
            .withEntity(HttpEntities.create(ContentTypes.APPLICATION_JSON, zaphod())))
            .assertStatusCode(201);

  }

  private String alice() {
    return "{\"id\":1,\"name\":\"Alice\"}";
  }

  private String zaphod() {
    return "{\"id\":42,\"name\":\"Zaphod\"}";
  }

}

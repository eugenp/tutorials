package com.baeldung.akkahttp;

import org.junit.Test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.model.ContentType;
import akka.http.javadsl.model.ContentType.NonBinary;
import akka.http.javadsl.model.HttpHeader;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.MediaType;
import akka.http.javadsl.model.MediaTypes;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.model.headers.RawHeader;
import akka.http.javadsl.testkit.JUnitRouteTest;
import akka.http.javadsl.testkit.TestRoute;

public class UserRoutesTest extends JUnitRouteTest {

    ActorSystem system = ActorSystem.create("helloAkkaHttpServer");
    ActorRef userActorRef = system.actorOf(UserActor.props(), "userActor");

    TestRoute appRoute = testRoute(new UserRoutes(system, userActorRef).routes());

    @Test
    public void givenUser_WhenGetOrPostUsers_ThenUserRetrieved() {

        appRoute.run(HttpRequest.GET("/users"))
            .assertStatusCode(200)
            .assertContentType("application/json")
            .assertEntity("{\"users\":[]}");

        appRoute.run(HttpRequest.GET("/usersw"))
            .assertStatusCode(404);

        appRoute.run(HttpRequest.POST("/users"))
            .assertStatusCode(400);

        final RawHeader contentTypeHeader = RawHeader.create("Content-Type", "application/json");
        final RawHeader acceptHeader = RawHeader.create("", "application/json");

        appRoute.run(HttpRequest.POST("/users")
            .addHeader(contentTypeHeader)
            .addHeader(acceptHeader))
            .assertStatusCode(400);

    }
}

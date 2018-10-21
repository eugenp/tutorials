package com.baeldung.akkahttp;

import java.util.concurrent.CompletionStage;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.AllDirectives;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

public class AkkaHttpServer extends AllDirectives {

    private final UserRoutes userRoutes;

    public AkkaHttpServer(ActorSystem system, ActorRef userRegistryActor) {
        userRoutes = new UserRoutes(system, userRegistryActor);
    }

    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create("helloAkkaHttpServer");
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        ActorRef userActorRef = system.actorOf(UserActor.props(), "userActor");

        AkkaHttpServer app = new AkkaHttpServer(system, userActorRef);

        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = app.userRoutes.routes()
            .flow(system, materializer);

        final CompletionStage<ServerBinding> binding = Http.get(system)
            .bindAndHandle(routeFlow, ConnectHttp.toHost("localhost", 8080), materializer);

        System.out.println("Server is online at http://localhost:8080/");
    }
}
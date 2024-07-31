package com.baeldung.helidon.se.webserver;

import io.helidon.webserver.Routing;
import io.helidon.webserver.WebServer;

public class SimpleWebApplication {

    public static void main(String... args) {

        Routing routing = Routing.builder()
                .get("/greet", (request, response) -> response.send("Hello World !"))
                .build();

        WebServer.builder(routing)
                .port(9001).addRouting(routing).build()
                .start()
                .thenAccept(ws ->
                        System.out.println("Server started at: http://localhost:" + ws.port())
                );
    }

}

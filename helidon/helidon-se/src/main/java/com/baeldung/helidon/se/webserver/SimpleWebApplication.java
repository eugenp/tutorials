package com.baeldung.helidon.se.webserver;

import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerConfiguration;
import io.helidon.webserver.WebServer;

public class SimpleWebApplication {

    public static void main(String... args) throws Exception {

        ServerConfiguration serverConfig = ServerConfiguration.builder()
                .port(9001)
                .build();

        Routing routing = Routing.builder()
                .get("/greet", (request, response) -> response.send("Hello World !"))
                .build();

        WebServer.create(serverConfig, routing)
                .start()
                .thenAccept(ws ->
                        System.out.println("Server started at: http://localhost:" + ws.port())
                );
    }

}

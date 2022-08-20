package com.baeldung.helidon.se.routing;

import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerConfiguration;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.json.JsonSupport;

public class WebApplicationRouting {

    public static void main(String... args) throws Exception {

        ServerConfiguration serverConfig = ServerConfiguration.builder()
                .port(9080)
                .build();

        Routing routing = Routing.builder()
                .register(JsonSupport.get())
                .register("/books", new BookResource())
                .get("/greet", (request, response) -> response.send("Hello World !"))
                .build();

        WebServer.create(serverConfig, routing)
                .start()
                .thenAccept(ws ->
                        System.out.println("Server started at: http://localhost:" + ws.port())
                );
    }

}

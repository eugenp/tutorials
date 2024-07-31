package com.baeldung.helidon.se.routing;

import io.helidon.webserver.Routing;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.json.JsonSupport;

public class WebApplicationRouting {

    public static void main(String... args) {

        Routing routing = Routing.builder()
                .register(JsonSupport.create())
                .register("/books", new BookResource())
                .get("/greet", (request, response) -> response.send("Hello World !"))
                .build();

        WebServer.builder().port(9080).addRouting(routing)
                .build()
                .start()
                .thenAccept(ws ->
                        System.out.println("Server started at: http://localhost:" + ws.port())
                );
    }

}

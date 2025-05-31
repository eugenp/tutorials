package com.baeldung.armeria;

import com.linecorp.armeria.common.HttpMethod;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.MediaType;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.ServerBuilder;
import com.linecorp.armeria.server.logging.AccessLogWriter;

import java.util.concurrent.CompletableFuture;

public class FluentRoutesServer {
    public static void main(String[] args) {
        ServerBuilder sb = Server.builder();
        sb.http(8080);

        sb.tlsSelfSigned();
        sb.https(8443);

        sb.accessLogWriter(AccessLogWriter.common(), true);

        sb.route()
            .methods(HttpMethod.GET)
            .path("/get")
            .produces(MediaType.PLAIN_TEXT)
            .matchesParams("name")
            .build((ctx, req) -> HttpResponse.of("Hello, " + ctx.path()));

        Server server = sb.build();
        CompletableFuture<Void> future = server.start();
        future.join();
    }

}

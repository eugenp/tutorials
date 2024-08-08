package com.baeldung.armeria;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.ServerBuilder;
import com.linecorp.armeria.server.logging.AccessLogWriter;

import java.util.concurrent.CompletableFuture;

public class PathParamsServer {
    public static void main(String[] args) {
        ServerBuilder sb = Server.builder();
        sb.http(8080);

        sb.accessLogWriter(AccessLogWriter.common(), true);

        sb.service("/handler", (ctx, req) -> HttpResponse.of("Hello, world!"));
        sb.service("/curly/{name}", (ctx, req) -> HttpResponse.of("Hello, " + ctx.pathParam("name")));
        sb.service("/colon/:name", (ctx, req) -> HttpResponse.of("Hello, " + ctx.pathParam("name")));
        sb.service("glob:/base/*/glob/**", (ctx, req) -> HttpResponse.of("Hello, " + ctx.pathParam("0") + ", " + ctx.pathParam("1")));
        sb.service("regex:^/regex/[A-Za-z]+/[0-9]+$", (ctx, req) -> HttpResponse.of("Hello, " + ctx.path()));
        sb.service("regex:^/named-regex/(?<name>[A-Z][a-z]+)$", (ctx, req) -> HttpResponse.of("Hello, " + ctx.pathParam("name")));

        Server server = sb.build();
        CompletableFuture<Void> future = server.start();
        future.join();
    }

}

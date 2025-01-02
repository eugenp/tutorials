package com.baeldung.jersey.timeout.server;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;

import com.baeldung.jersey.server.EmbeddedJerseyServer;

public class JerseyTimeoutServerApp {

    public static final URI BASE_URI = URI.create("http://localhost:8082");

    public static void main(String... args) throws IOException {
        start();
    }

    public static HttpServer start() throws IOException {
        return EmbeddedJerseyServer.start(BASE_URI, new TimeoutAppConfig());
    }
}

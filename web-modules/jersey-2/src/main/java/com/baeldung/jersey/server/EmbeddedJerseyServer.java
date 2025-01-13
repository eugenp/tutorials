package com.baeldung.jersey.server;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmbeddedJerseyServer {

    private static final Logger logger = LoggerFactory.getLogger(EmbeddedJerseyServer.class);

    private EmbeddedJerseyServer() {
    }

    public static HttpServer start(URI url, ResourceConfig config) throws IOException {
        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(url.toString()), config, false);

        Runtime.getRuntime()
            .addShutdownHook(new Thread(server::shutdownNow));

        server.start();
        logger.info("server available at {}", url);
        return server;
    }
}

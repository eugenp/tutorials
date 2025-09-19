package com.baeldung.context;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.junit.jupiter.api.*;

import java.net.InetSocketAddress;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseServletTest {

    protected HttpClient httpClient;
    protected Server server;

    protected int port() {
        return 0; // (random) available port
    }

    protected String host() {
        return "localhost";
    }

    protected String contextPath() {
        return "/";
    }

    @BeforeAll
    void startup() throws Exception {
        httpClient = new HttpClient();
        httpClient.start();

        ServletContextHandler context = prepareContextHandler();

        server = new Server(new InetSocketAddress(host(), port()));
        server.setHandler(context);
        server.start();
    }

    private ServletContextHandler prepareContextHandler() {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(contextPath());
        configure(context);
        return context;
    }

    protected abstract void configure(ServletContextHandler context);

    @AfterAll
    void shutdown() throws Exception {
        if (server != null) {
            server.stop();
        }
        if (httpClient != null) {
            httpClient.stop();
        }
    }

    protected String baseUri() {
        String uri = server.getURI()
            .toString();
        return uri.endsWith("/") ? uri.substring(0, uri.length() - 1) : uri;
    }
}

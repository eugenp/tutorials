package com.baeldung.http3;

import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Http3DemoTest {

    private HttpServer server;
    private String baseUrl;

    @BeforeEach
    void setUp() throws IOException {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/hello", exchange -> {
            byte[] body = "Hello, HTTP/3!".getBytes();
            exchange.sendResponseHeaders(200, body.length);
            try (OutputStream out = exchange.getResponseBody()) {
                out.write(body);
            }
        });
        server.start();
        baseUrl = "http://127.0.0.1:" + server.getAddress().getPort();
    }

    @AfterEach
    void tearDown() {
        server.stop(0);
    }

    @Test
    void givenValidUrl_whenFetch_thenReturnsResponseBody() throws Exception {
        HttpResponse<String> response = new Http3Demo().fetch(baseUrl + "/hello");

        assertEquals(200, response.statusCode());
        assertEquals("Hello, HTTP/3!", response.body());
    }
}
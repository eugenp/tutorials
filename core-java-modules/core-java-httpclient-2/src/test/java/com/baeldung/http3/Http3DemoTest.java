package com.baeldung.http3;

import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.UnsupportedProtocolVersionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Http3DemoTest {

    private static final String HTTP3_URL = "https://cloudflare-quic.com/";

    private HttpServer plainServer;
    private String plainBaseUrl;

    @BeforeEach
    void setUp() throws IOException {
        plainServer = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        plainServer.createContext("/hello", exchange -> {
            byte[] body = "Hello, HTTP/3!".getBytes();
            exchange.sendResponseHeaders(200, body.length);
            try (OutputStream out = exchange.getResponseBody()) {
                out.write(body);
            }
        });
        plainServer.start();
        plainBaseUrl = "http://127.0.0.1:" + plainServer.getAddress().getPort();
    }

    @AfterEach
    void tearDown() {
        plainServer.stop(0);
    }


    @Tag("live")
    @Test
    void givenValidHttpsUrl_whenFetch_thenReturnsResponseBody() {
        HttpResponse<String> response;
        try {
            response = new Http3Demo().fetch(HTTP3_URL);
        } catch (IOException | InterruptedException e) {
            Assumptions.abort("Skipping: cloudflare-quic.com unreachable in this environment - " + e.getMessage());
            return;
        }
        assertEquals(200, response.statusCode());
        assertEquals(HttpClient.Version.HTTP_3, response.version());
    }

    @Test
    void givenPlainHttpUrl_whenFetch_thenThrowsUnsupportedProtocolVersionException() {
        assertThrows(
                UnsupportedProtocolVersionException.class,
                () -> new Http3Demo().fetch(plainBaseUrl + "/hello")
        );
    }
}
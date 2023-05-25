package com.baeldung.springcloudgateway.rewrite;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.OutputStream;
import java.net.InetSocketAddress;

import org.junit.AfterClass;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.sun.net.httpserver.HttpServer;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles({ "nosecurity", "url-rewrite" })
class URLRewriteGatewayApplicationLiveTest {

    // NOTE for Eclipse users: By default, Eclipse will complain about com.sun.** classes.
    // To solve this issue, follow instructions available at the :
    // https://stackoverflow.com/questions/13155734/eclipse-cant-recognize-com-sun-net-httpserver-httpserver-package
    private static HttpServer mockServer;
    private static Logger log = LoggerFactory.getLogger(URLRewriteGatewayApplicationLiveTest.class);

    // Create a running HttpServer that echoes back the request URL.
    private static HttpServer startTestServer() {

        try {
            log.info("[I26] Starting mock server");
            mockServer = HttpServer.create();
            mockServer.bind(new InetSocketAddress(0), 0);
            mockServer.createContext("/api", (xchg) -> {
                String uri = xchg.getRequestURI()
                    .toString();
                log.info("[I23] Backend called: uri={}", uri);
                xchg.getResponseHeaders()
                    .add("Content-Type", "text/plain");
                xchg.sendResponseHeaders(200, 0);
                OutputStream os = xchg.getResponseBody();
                os.write(uri.getBytes());
                os.flush();
                os.close();
            });

            mockServer.start();
            InetSocketAddress localAddr = mockServer.getAddress();
            log.info("[I36] mock server started: local address={}", localAddr);
            
            return mockServer;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    // TIP: https://www.baeldung.com/spring-dynamicpropertysource
    @DynamicPropertySource
    static void registerBackendServer(DynamicPropertyRegistry registry) {
        registry.add("rewrite.backend.uri", () -> {
            HttpServer s = startTestServer();
            return "http://localhost:" + s.getAddress().getPort();
        });
    }

    @AfterClass
    public static void stopMockBackend() throws Exception {
        log.info("[I40] Shutdown mock http server");
        mockServer.stop(5);
    }

    @LocalServerPort
    private int localPort;

    @Test
    void testWhenApiCall_thenRewriteSuccess(@Autowired WebTestClient webClient) {
        webClient.get()
          .uri("http://localhost:" + localPort + "/v1/customer/customer1")
          .exchange()
          .expectBody()
          .consumeWith((result) -> {
              String body = new String(result.getResponseBody());
              log.info("[I99] body={}", body);
              assertEquals("/api/customer1", body);
          });
    }

    @Test
    void testWhenDslCall_thenRewriteSuccess(@Autowired WebTestClient webClient) {
        webClient.get()
          .uri("http://localhost:" + localPort + "/v2/zip/123456")
          .exchange()
          .expectBody()
          .consumeWith((result) -> {
              String body = new String(result.getResponseBody());
              log.info("[I99] body={}", body);
              assertTrue(body.matches("/api/zip/123456-\\d{3}"));
          });
    }
    
}

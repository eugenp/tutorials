package com.baeldung.activej;

import com.baeldung.activej.config.PersonModule;
import com.baeldung.activej.controller.PersonController;
import com.baeldung.activej.model.VerifiedPerson;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.activej.dns.DnsClient;
import io.activej.eventloop.Eventloop;
import io.activej.http.*;
import io.activej.inject.Injector;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class ActiveJIntegrationTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static HttpServer server;
    private static HttpClient client;
    private static int port;
    private static Eventloop eventloop;

    @BeforeAll
    static void setUp() throws Exception {
        eventloop = Eventloop.create();

        PersonModule personModule = new PersonModule();
        PersonController personController = Injector.of(personModule).getInstance(PersonController.class);

        RoutingServlet servlet = RoutingServlet.builder(eventloop)
          .with(HttpMethod.GET,"/person", personController)
          .build();

        server = HttpServer.builder(eventloop, servlet)
                .withListenPort(randomPort())
                .build();

        server.listen();

        port = server.getListenAddresses().get(0).getPort();

        InetAddress dnsServerAddress = InetAddress.getByName("8.8.8.8");
        DnsClient dnsClient = DnsClient.builder(eventloop, dnsServerAddress).build();
        client = HttpClient.builder(eventloop, dnsClient).build();
    }

    private static int randomPort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        } catch (IOException e) {
            throw new RuntimeException("Failed to find a free port", e);
        }
    }

    @AfterAll
    static void tearDown() {
        if (server != null) {
            server.close();
        }
    }

    @Test
    void givenHttpServer_whenCallPersonEndpoint_thenExpectedVerificationResultShouldPresentInResponse() {
        HttpRequest request = HttpRequest.get("http://localhost:" + port + "/person?name=my-name").build();

        client.request(request)
          .whenResult(response -> {
              assertEquals(response.getCode(), 200);

              response.loadBody()
                .whenResult(body -> {
                    try {
                        VerifiedPerson responseData = objectMapper.readValue(body.getArray(),
                          VerifiedPerson.class);
                        assertEquals(responseData.result(), "FAIL");
                        eventloop.breakEventloop();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
          });

        eventloop.run();
    }
}

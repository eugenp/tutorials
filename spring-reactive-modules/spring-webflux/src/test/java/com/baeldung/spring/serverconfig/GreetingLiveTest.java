package com.baeldung.spring.serverconfig;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import javax.net.ssl.SSLException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;
import reactor.netty.http.client.HttpClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext
public class GreetingLiveTest {

    private static final String BASE_URL = "https://localhost:8443";

    private WebTestClient webTestClient;

    @Before
    public void setup() throws SSLException {
        webTestClient = WebTestClient.bindToServer(getConnector())
                                     .baseUrl(BASE_URL)
                                     .build();
    }

    @Test
    public void shouldGreet() {
        final String name = "Baeldung";

        ResponseSpec response = webTestClient.get()
                                             .uri("/greet/{name}", name)
                                             .exchange();

        response.expectStatus()
                .isOk()
                .expectBody(String.class)
                .isEqualTo("Greeting Baeldung");
    }

    private ReactorClientHttpConnector getConnector() throws SSLException {
        SslContext sslContext = SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
        HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));
        return new ReactorClientHttpConnector(httpClient);
    }
}

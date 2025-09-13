package com.baeldung.http2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;

// Integration test based on enabling HTTP/2 using the configuration in application.yml
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Http2ApplicationIntegrationTest {

    private static SSLContext sslContext;

    @LocalServerPort
    private int port;

    private HttpClient httpClient;

    @BeforeAll
    static void setupOnce() throws NoSuchAlgorithmException, KeyManagementException {
        // Get rid of cert validation due to the self-signed certificate
        sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null,
                new TrustManager[] { new AllCertsTrustManager() },
                new java.security.SecureRandom());
    }

    @BeforeEach
    void setup() {
        httpClient = HttpClient.newBuilder()
                .sslContext(sslContext)
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    @Test
    void whenSendHttp2Request_thenReturnHttp2Response() throws Exception {
        // when
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(String.format("https://localhost:%s/http2/echo?message=test", port)))
                .GET()
                .build();
        HttpResponse<?> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // then
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isEqualTo("test");
        assertThat(response.version()).isEqualTo(HttpClient.Version.HTTP_2);
    }

}
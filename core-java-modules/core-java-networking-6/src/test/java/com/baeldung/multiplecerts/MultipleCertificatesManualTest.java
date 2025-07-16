package com.baeldung.multiplecerts;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.DefaultClientTlsStrategy;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.ssl.SSLContexts;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.Options;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

/**
 * 1. run gen-keys.sh for api.service1 and api.service2 to generate server certificate/stores
 * 2. specify system properties specifying the directory of the certificates and chosen password
 * 3. create api.service1 and api.service2 entries for 127.0.0.1 in /etc/host
 * 4. run this class
 */
class MultipleCertificatesManualTest {

    static final String CERTS_DIR = System.getProperty("certs.dir");
    static final String PASSWORD = System.getProperty("certs.password");

    static WireMockServer api1;
    static WireMockServer api2;

    @BeforeAll
    static void setup() {
        api1 = mockHttpsServer("api.service1", 10443);
        stubTest(api1, "ok from server 1");
        api1.start();

        api2 = mockHttpsServer("api.service2", 20443);
        stubTest(api2, "ok from server 2");
        api2.start();
    }

    @AfterAll
    static void teardown() {
        api1.stop();
        api2.stop();
    }

    @Test
    void whenBuildingSeparateContexts_thenCorrectCertificateUsed() throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
        CloseableHttpClient client1 = httpsClient("api.service1");

        HttpGet api1Get = new HttpGet(testUrl(api1));
        client1.execute(api1Get, response -> {
            assertEquals(HttpStatus.SC_OK, response.getCode());
            return response;
        });

        CloseableHttpClient client2 = httpsClient("api.service2");

        HttpGet api2Get = new HttpGet(testUrl(api2));
        client2.execute(api2Get, response -> {
            assertEquals(HttpStatus.SC_OK, response.getCode());
            return response;
        });
    }

    @Test
    void whenBuildingCustomSslContext_thenCorrectCertificateUsedForEachConnection() throws Exception {
        SSLContext context = RoutingSslContextBuilder.create()
            .trust("api.service1", CERTS_DIR, PASSWORD)
            .trust("api.service2", CERTS_DIR, PASSWORD)
            .build();

        HttpClient client = HttpClient.newBuilder()
            .sslContext(context)
            .build();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(testUrl(api1)))
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertEquals("ok from server 1", response.body());

        request = HttpRequest.newBuilder()
            .uri(URI.create(testUrl(api2)))
            .GET()
            .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertEquals("ok from server 2", response.body());
    }

    private CloseableHttpClient httpsClient(String host) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException, CertificateException, IOException {
        char[] password = PASSWORD.toCharArray();

        SSLContext context = SSLContexts.custom()
            .loadTrustMaterial(Paths.get(CERTS_DIR + "/trust." + host + ".p12"), password)
            .loadKeyMaterial(Paths.get(CERTS_DIR + "/client." + host + ".p12"), password, password)
            .build();

        PoolingHttpClientConnectionManager manager = PoolingHttpClientConnectionManagerBuilder.create()
            .setTlsSocketStrategy(new DefaultClientTlsStrategy(context))
            .build();

        return HttpClients.custom()
            .setConnectionManager(manager)
            .build();
    }

    private static WireMockServer mockHttpsServer(String host, int port) {
        return new WireMockServer(WireMockConfiguration.options()
            .bindAddress(host)
            .dynamicPort()
            .httpsPort(port)
            .trustStorePath(CERTS_DIR + "/trust." + host + ".p12")
            .trustStorePassword(PASSWORD)
            .keystorePath(CERTS_DIR + "/server." + host + ".p12")
            .keystorePassword(PASSWORD)
            .keyManagerPassword(PASSWORD)
            .needClientAuth(true));
    }

    private static void stubTest(WireMockServer server, String response) {
        server.stubFor(get(urlEqualTo("/test")).willReturn(aResponse().withHeader("Content-Type", "text/plain")
            .withStatus(200)
            .withBody(response)));
    }

    private String testUrl(WireMockServer server) {
        Options options = server.getOptions();
        return String.format("https://%s:%d/test", options.bindAddress(), options.httpsSettings()
            .port());
    }
}

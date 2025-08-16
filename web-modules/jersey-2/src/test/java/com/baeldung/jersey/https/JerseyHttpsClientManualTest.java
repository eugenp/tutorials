package com.baeldung.jersey.https;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;

import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;

/**
 * 1. run {@code gen-keys.sh api.service} to generate server certificate/stores
 * 2. specify system properties with the directory of the certificates and password
 * 3. create api.service entries for 127.0.0.1 in /etc/hosts
 * 4. run this class
 */
class JerseyHttpsClientManualTest {

    static final String MOCK_HOST = "api.service";
    static final String CERTS_DIR = System.getProperty("certs.dir");
    static final String PASSWORD = System.getProperty("certs.password");

    WireMockServer api;

    void setup(boolean mTls) {
        api = mockHttpsServer(mTls);
        api.stubFor(get(urlEqualTo("/test")).willReturn(aResponse().withHeader("Content-Type", "text/plain")
            .withStatus(200)
            .withBody("ok")));
        api.start();
        
        System.out.println(">> Mock server started on HTTPS port: " + api.httpsPort());
    }

    @AfterEach
    void teardown() {
        if (api != null) {
            api.stop();
        }
    }

    @Test
    void whenUsingJVMParameters_thenCorrectCertificateUsed() {
        setup(false);

        System.setProperty("javax.net.ssl.trustStore", CERTS_DIR + "/trust." + MOCK_HOST + ".p12");
        System.setProperty("javax.net.ssl.trustStorePassword", PASSWORD);

        Response response = ClientBuilder.newClient().target(String.format("https://%s:%d/test", api.getOptions().bindAddress(), api.httpsPort()))
            .request()
            .get();

        assertEquals(200, response.getStatus());
    }

    @Test
    void whenUsingCustomSSLContext_thenCorrectCertificateUsed() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
        setup(false);

        SSLContext sslContext = SSLContextBuilder.create()
            .loadTrustMaterial(Paths.get(CERTS_DIR + "/trust." + MOCK_HOST + ".p12"), PASSWORD.toCharArray())
            .build();

        Client client = ClientBuilder.newBuilder()
            .sslContext(sslContext)
            .build();

        Response response = client.target(String.format("https://%s:%d/test", api.getOptions().bindAddress(), api.httpsPort()))
            .request()
            .get();
        assertEquals(200, response.getStatus());
    }

    @Test
    void whenUsingMutualTLS_thenCorrectCertificateUsed() throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
        setup(true);
        
        char[] password = PASSWORD.toCharArray();

        SSLContext sslContext = SSLContextBuilder.create()
            .loadTrustMaterial(Paths.get(CERTS_DIR + "/trust." + MOCK_HOST + ".p12"), password)
            .loadKeyMaterial(Paths.get(CERTS_DIR + "/client." + MOCK_HOST + ".p12"), password, password)
            .build();

        Client client = ClientBuilder.newBuilder()
            .sslContext(sslContext)
            .build();

        Response response = client.target(String.format("https://%s:%d/test", api.getOptions().bindAddress(), api.httpsPort()))
            .request()
            .get();
        assertEquals(200, response.getStatus());
    }

    private static WireMockServer mockHttpsServer(boolean mTls) {
        WireMockConfiguration config = WireMockConfiguration.options()
            .bindAddress(MOCK_HOST)
            .dynamicPort()
            .dynamicHttpsPort()
            .keystorePath(CERTS_DIR + "/server." + MOCK_HOST + ".p12")
            .keystorePassword(PASSWORD)
            .keyManagerPassword(PASSWORD);
        
        if (mTls) {
            config.trustStorePath(CERTS_DIR + "/trust." + MOCK_HOST + ".p12")
                .trustStorePassword(PASSWORD)
                .needClientAuth(true);
        }
        
        return new WireMockServer(config);
    }
}

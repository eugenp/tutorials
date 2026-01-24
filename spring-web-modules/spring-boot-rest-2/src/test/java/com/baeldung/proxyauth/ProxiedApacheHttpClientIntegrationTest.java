package com.baeldung.proxyauth;

import static com.baeldung.proxyauth.ProxiedApacheHttpClient.createClient;
import static com.baeldung.proxyauth.ProxiedApacheHttpClient.sendRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.configuration.Configuration;
import org.mockserver.integration.ClientAndServer;

class ProxiedApacheHttpClientIntegrationTest {

    private ClientAndServer proxyServer;
    private ClientAndServer resourceServer;
    private int proxyPort;
    private int resourcePort;

    @BeforeEach
    void setUp() {
        resourceServer = ClientAndServer.startClientAndServer(0);
        resourcePort = resourceServer.getPort();
    }

    @AfterEach
    void tearDown() {
        if (proxyServer != null) {
            proxyServer.stop();
        }
        if (resourceServer != null) {
            resourceServer.stop();
        }
    }

    @Test
    void givenAuthenticatedProxy_whenSendRequest_thenSuccess() throws Exception {
        Configuration config = Configuration.configuration()
            .proxyAuthenticationRealm("MockServer Realm")
            .proxyAuthenticationUsername("testuser")
            .proxyAuthenticationPassword("testpass");

        proxyServer = ClientAndServer.startClientAndServer(config, 0);
        proxyPort = proxyServer.getPort();

        resourceServer.when(request().withMethod("GET")
            .withPath("/secure"))
            .respond(response().withStatusCode(200)
                .withBody("Authenticated Response"));

        ProxyConfig authProxyConfig = new ProxyConfig("localhost", proxyPort, "testuser", "testpass");

        CloseableHttpClient client = createClient(authProxyConfig);
        String response = sendRequest(client, "http://localhost:" + resourcePort + "/secure");

        assertEquals("Authenticated Response", response);
    }
}

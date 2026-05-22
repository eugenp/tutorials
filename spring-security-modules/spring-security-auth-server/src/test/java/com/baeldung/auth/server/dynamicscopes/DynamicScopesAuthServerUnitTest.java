package com.baeldung.auth.server.dynamicscopes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integrations tests for the {@link DynamicScopesAuthServerApplication}
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dynamic-scopes")
public class DynamicScopesAuthServerUnitTest {

    @LocalServerPort
    int port;

    private RestTestClient restTestClient;

    @Autowired
    ApplicationContext ctx;

    @Test
    void whenAuthorizationRequest_thenSuccess() {

        assertNotNull(ctx);
        var response = restTestClient.get().uri("/.well-known/openid-configuration").exchange();

        // sanity check
        var result = response.returnResult(Map.class);
        var config = result.getResponseBody();
        assertTrue(result.getStatus().is2xxSuccessful());
        assertNotNull(config);
        assertTrue(config.containsKey("token_endpoint"));
        assertNotNull(config.get("token_endpoint"));
        assertTrue(config.containsKey("authorization_endpoint"));
        assertNotNull(config.get("authorization_endpoint"));

        var authEndpoint = URI.create(config.get("authorization_endpoint").toString());
        var tokenEndpoint = config.get("token_endpoint").toString();

        // Build auth request
        var txId = UUID.randomUUID().toString();
        var state = UUID.randomUUID().toString();
        var authResponse = restTestClient.get()
          .uri( b -> b.path(authEndpoint.getPath())
            .queryParam("response_type", "code")
            .queryParam("client_id", "client1")
            .queryParam("scope", "openid email")
            .queryParam("scope", String.join(" ","openid","TX:" + txId))
            .queryParam("redirect_uri", "http://localhost:9090/login/oauth2/code/issuer1client1")
            .queryParam("state", state)
            .build())
          .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
          .header("Cache-Control", "no-cache")
          .exchange();
        var authResult = authResponse.returnResult();
        var location = authResult.getResponseHeaders().getLocation();

        assertEquals(HttpStatus.FOUND,authResult.getStatus());

        assertNotNull(location);
    }

    @BeforeEach
    void setupRestClient() {
        restTestClient = RestTestClient.bindToServer()
          .baseUrl("http://localhost:" + port)
          .build();
    }

}
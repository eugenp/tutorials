package com.baeldung.auth.server.dynamicscopes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.CookieManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.client.RestTestClient.bindToServer;

/**
 * Integrations tests for the {@link DynamicScopesAuthServerApplication}
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dynamic-scopes")
public class DynamicScopesAuthServerUnitTest {

    @LocalServerPort
    int port;

    RestTestClient restTestClient, noRedirecRestTestClient;

    @Autowired
    ApplicationContext ctx;

    private static final String ACCEPT_HEADER_VALUE = "text/html";


    // Happy path integration test
    @Test
    void whenAuthorizationRequestWithDynamicScope_thenSuccess() {

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
        var redirectUri = "http://localhost:9090/login/oauth2/code/issuer1client1";
        var authResponse = restTestClient.get()
          .uri( b -> b.path(authEndpoint.getPath())
            .queryParam("response_type", "code")
            .queryParam("client_id", "client1")
            .queryParam("scope", String.join(" ","openid","TX:" + txId))
            .queryParam("redirect_uri", redirectUri)
            .queryParam("state", state)
            .build())
          .header("Accept", ACCEPT_HEADER_VALUE)
          .header("Cache-Control", "no-cache")
          .exchange();
        var authResult = authResponse.returnResult();

        assertEquals(HttpStatus.OK,authResult.getStatus());
        var loginPage = new  String(authResult.getResponseBodyContent());
        var doc = Jsoup.parse(loginPage);

        // Extract the login form submit URI and the csrf token
        var loginFormCsrfToken = doc.expectForm(".login-form").select("input[name=_csrf]").first().val();
        assertNotNull(loginFormCsrfToken);

        // Extract the URI to submit the credentials
        var loginUri = doc.expectForm(".login-form").attr("action");

        // Submit the credentials
        var loginBody = new LinkedMultiValueMap<String, String>();
        loginBody.add("username", "user");
        loginBody.add("password", "password");
        loginBody.add("_csrf", loginFormCsrfToken);

        var loginResponse = restTestClient.post()
          .uri(loginUri)
          .header("Accept", ACCEPT_HEADER_VALUE)
          .header("Cache-Control", "no-cache")
          .contentType(MediaType.APPLICATION_FORM_URLENCODED)
          .body(loginBody)
          .exchange();

        var loginResult = loginResponse.returnResult();
        assertEquals(HttpStatus.OK,loginResult.getStatus());

        // We should be redirected to the consent page with the requested scopes
        var consentPage = new String(loginResult.getResponseBodyContent());
        var consentDoc = Jsoup.parse(consentPage);

        // The list of scopes should contain the dynamic scope with the transaction id
        var scopes = consentDoc.select("input[name=scope]");
        assertEquals("TX:" + txId,scopes.getFirst().val());

        // Get the new csrf token
        var consentFormCsrfToken = consentDoc.expectForm("form[name=consent_form]").select("input[name=_csrf]").first().val();
        assertNotNull(consentFormCsrfToken);

        // Get the consent state. Notice that this value is not the same as the initial state
        var consentState = consentDoc.select("input[name=state]").first().val();
        assertNotNull(consentState);

        // Get the consent form target
        var consentUri = consentDoc.expectForm("form[name=consent_form]").attr("action");
        var consentBody = new LinkedMultiValueMap<String, String>();
        consentBody.add("scope", "TX:" + txId);
        consentBody.add("client_id", "client1");
        consentBody.add("state", consentState);
        consentBody.add("_csrf", consentFormCsrfToken);

        var consentResponse = noRedirecRestTestClient.post()
          .uri(consentUri)
          .contentType(MediaType.APPLICATION_FORM_URLENCODED)
          .body(consentBody)
          .exchange();

        var consentResult = consentResponse.returnResult();
        assertEquals(HttpStatus.FOUND,consentResult.getStatus());
        var location = consentResult.getResponseHeaders().getLocation();
        assertNotNull(location);
        assertTrue(location.getQuery().contains("code="));

        var locationParams = UriComponentsBuilder.fromUri(location).build().getQueryParams();
        assertNotNull(locationParams.get("code"));

        var tokenRequestBody = new LinkedMultiValueMap<String, String>();
        tokenRequestBody.add("grant_type", "authorization_code");
        tokenRequestBody.add("client_id", "client1");
        tokenRequestBody.add("redirect_uri", redirectUri);
        tokenRequestBody.add("code", locationParams.getFirst("code"));

        // Generate an access token
        var tokenResponse = restTestClient.post()
          .uri(tokenEndpoint)
          .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("client1:secret1".getBytes()))
          .body(tokenRequestBody)
          .exchange();

        var tokenResult = tokenResponse.returnResult(Map.class);
        assertEquals(HttpStatus.OK, tokenResult.getStatus());
        var body = tokenResult.getResponseBody();
        assertNotNull(body);
        assertTrue(body.containsKey("access_token"));
        assertTrue(body.containsKey("scope"));

        // The returned scope should include the requested dynamic scope
        assertTrue(body.get("scope").toString().contains("TX:" + txId));

    }

    @BeforeEach
    void setupRestClient() {

        CookieManager cookieManager = new CookieManager();

        var followRedirectsHttpClient = HttpClient.newBuilder()
          .followRedirects(HttpClient.Redirect.ALWAYS)
          .cookieHandler(cookieManager)
          .build();

        var followRedirectsRequestFactory = new JdkClientHttpRequestFactory(followRedirectsHttpClient);
        restTestClient = RestTestClient
          .bindToServer(followRedirectsRequestFactory)
          .baseUrl("http://localhost:" + port)
          .build();

        var noRedirectsHttpClient = HttpClient.newBuilder()
          .followRedirects(HttpClient.Redirect.NEVER)
          .cookieHandler(cookieManager)
          .build();

        var noRedirectsRequestFactory = new JdkClientHttpRequestFactory(noRedirectsHttpClient);
        noRedirecRestTestClient = RestTestClient
          .bindToServer(noRedirectsRequestFactory)
          .baseUrl("http://localhost:" + port)
          .build();

    }

}
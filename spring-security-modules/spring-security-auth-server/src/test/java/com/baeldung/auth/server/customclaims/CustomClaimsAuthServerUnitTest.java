package com.baeldung.auth.server.customclaims;

import com.baeldung.auth.server.dynamicscopes.DynamicScopesAuthServerApplication;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
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

/**
 * Integrations tests for the {@link CustomClaimsAuthServerApplication}
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("customclaims")
class CustomClaimsAuthServerUnitTest {

    @LocalServerPort
    int port;

    RestTestClient restTestClient, noRedirecRestTestClient;

    private static final String ACCEPT_HEADER_VALUE = "text/html";


    // Happy path integration test
    @Test
    void whenAuthorizationRequestWithValidCredentials_thenSuccess() {

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
        var state = UUID.randomUUID().toString();
        var redirectUri = "http://localhost:9090/login/oauth2/code/issuer1client1";
        var authResponse = restTestClient.get()
          .uri( b -> b.path(authEndpoint.getPath())
            .queryParam("response_type", "code")
            .queryParam("client_id", "client1")
            .queryParam("scope", String.join(" ","openid","email","profile"))
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
        var tokenElement = doc.expectForm(".login-form").select("input[name=_csrf]").first();
        assertNotNull(tokenElement);
        var loginFormCsrfToken = tokenElement.val();
        assertNotNull(loginFormCsrfToken);

        // Extract the URI to submit the credentials
        var loginUri = doc.expectForm(".login-form").attr("action");

        // Submit the credentials
        var loginBody = new LinkedMultiValueMap<String, String>();
        loginBody.add("username", "user");
        loginBody.add("password", "password");
        loginBody.add("_csrf", loginFormCsrfToken);

        var loginResponse = noRedirecRestTestClient.post()
          .uri(loginUri)
          .header("Accept", ACCEPT_HEADER_VALUE)
          .header("Cache-Control", "no-cache")
          .contentType(MediaType.APPLICATION_FORM_URLENCODED)
          .body(loginBody)
          .exchange();

        var loginResult = loginResponse.returnResult();
        assertEquals(HttpStatus.FOUND,loginResult.getStatus());

        // We expect a redirect to authorization endpoint with the same parameters as before. This
        // time, the user is authenticated, and we should get redirect with the authorization code
        authResponse = noRedirecRestTestClient.get()
          .uri( loginResult.getResponseHeaders().getLocation())
          .header("Cache-Control", "no-cache")
          .exchange();
        authResult = authResponse.returnResult();
        assertEquals(HttpStatus.FOUND, authResult.getStatus());
        assertNotNull( authResult.getResponseHeaders().getLocation());

        var finalRedirect = UriComponentsBuilder.fromUri(authResult.getResponseHeaders().getLocation()).build();
        var expectedUri = UriComponentsBuilder.fromUriString(redirectUri).build();

        assertEquals(expectedUri.getPath(), finalRedirect.getPath());
        assertTrue(finalRedirect.getQueryParams().containsKey("code"));

        // Build token request
        var tokenRequestBody = new LinkedMultiValueMap<String, String>();
        tokenRequestBody.add("grant_type", "authorization_code");
        tokenRequestBody.add("client_id", "client1");
        tokenRequestBody.add("redirect_uri", redirectUri);
        tokenRequestBody.add("code", finalRedirect.getQueryParams().getFirst("code"));

        // Submit token request to the authorization server's token endpoint
        var tokenResponse = restTestClient.post()
          .uri(tokenEndpoint)
          .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("client1:secret1".getBytes()))
          .body(tokenRequestBody)
          .exchange();

        var tokenResult = tokenResponse.returnResult(Map.class);
        assertEquals(HttpStatus.OK, tokenResult.getStatus());
        var body = tokenResult.getResponseBody();

        // We expect an access token, an id token and a refresh token
        assertNotNull(body);
        assertTrue(body.containsKey("access_token"));
        assertTrue(body.containsKey("refresh_token"));
        assertTrue(body.containsKey("scope"));
        assertTrue(body.containsKey("id_token"));

        // Finally, let's use the access token to issue a get request to the userinfo endpoint
        var userInfoResponse = restTestClient.get()
          .uri(config.get("userinfo_endpoint").toString())
          .header("Authorization", "Bearer " + body.get("access_token"))
          .exchange();
        var userInfoResult = userInfoResponse.returnResult(Map.class);
        assertEquals(HttpStatus.OK, userInfoResult.getStatus());
        var userInfo = userInfoResult.getResponseBody();
        assertNotNull(userInfo);

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
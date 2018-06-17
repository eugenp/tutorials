package com.baeldung.restassured.authentication;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import java.security.KeyStoreException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.matching.ContainsPattern;
import com.github.tomakehurst.wiremock.matching.RegexPattern;

import io.restassured.RestAssured;
import io.restassured.authentication.CertificateAuthSettings;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.specification.FilterableRequestSpecification;


public class RestAssuredAuthenticationIntegrationTest {
    private static WireMockServer wireMockServer;

    private static final String KEYSTORE_TYPE = "JKS";
    private static final String KEYSTORE_JKS = "/keystore.jks";
    private static final String KEY_STORE_PASSWORD = "baeldung";

    private static final String AUTHENTICATED_PATH = "/authenticated";
    private static final String APPLICATION_JSON = "application/json";
    private static final String DIGEST_HEADER_VALUE = "Digest realm=\"testrealm@baeldung.com\","
        + "qop=\"auth,auth-int\","
        + "nonce=\"dcd98b7102dd2f0e8b11d0f600bfb0c093\","
        + "opaque=\"5ccc069c403ebaf9f0171e9517f40e41\"";

    @BeforeClass
    public static void before() throws Exception {
        System.out.println("Setting up!");

        WireMockConfiguration wireMockConfiguration = wireMockConfig()
            .containerThreads(20) // Need more threads than default when running https
            .dynamicPort()
            .dynamicHttpsPort()
            .keystorePath(RestAssuredAuthenticationIntegrationTest.class.getResource(KEYSTORE_JKS).getPath())
            .keystoreType(KEYSTORE_TYPE)
            .keystorePassword(KEY_STORE_PASSWORD);
        wireMockServer = new WireMockServer(wireMockConfiguration);
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());
        RestAssured.port = wireMockServer.port();
    }

    @AfterClass
    public static void after()
    {
        wireMockServer.stop();
    }

    @Test
    public void givenUrl_whenTestPreemptiveBasicAuthentication_thenCorrect() {
        final String username = "someone";
        final String password = "secret";

        // Mock Basic
        // https://www.ietf.org/rfc/rfc2617.txt
        stubFor(get(urlEqualTo(AUTHENTICATED_PATH))
            .withBasicAuth(username, password)
            .willReturn(aResponse().withStatus(200)
                .withBody("OK")));

        given()
            .auth()
            .preemptive()
            .basic(username, password)
        .when()
            .get(AUTHENTICATED_PATH)
        .then()
            .assertThat()
            .statusCode(200)
            .body(is("OK"));
    }

    @Test
    public void givenUrl_whenTestChallengedBasicAuthentication_thenCorrect() {
        final String username = "someone";
        final String password = "secret";

        // Mock Basic
        // https://www.ietf.org/rfc/rfc2617.txt
        stubFor(get(urlEqualTo(AUTHENTICATED_PATH))
            .withBasicAuth(username, password)
            .willReturn(aResponse().withStatus(200)
                .withHeader("Content-Type", APPLICATION_JSON)
                .withBody("OK")));

        given()
            .auth()
                .basic(username, password)
        .when()
            .get(AUTHENTICATED_PATH)
        .then()
            .assertThat()
            .statusCode(200)
            .body(is("OK"));

    }

    @Test
    public void givenUrl_whenTestDigestAuthentication_thenCorrect() {
        final String scenarioName = "AuthorizationState";
        final String stateName = "Authorized";

        // Mock Digest
        // https://www.ietf.org/rfc/rfc7616.txt
        stubFor(get(urlEqualTo(AUTHENTICATED_PATH))
            .inScenario(scenarioName)
            .willSetStateTo(stateName)
            .willReturn(aResponse().withStatus(401)
                    .withHeader("WWW-Authenticate", DIGEST_HEADER_VALUE)));
        stubFor(get(urlEqualTo(AUTHENTICATED_PATH))
            .inScenario(scenarioName)
            .whenScenarioStateIs(stateName)
            .withHeader("Authorization", new ContainsPattern("Digest "))
            .willReturn(aResponse().withStatus(200)
                    .withHeader("Content-Type", APPLICATION_JSON)
                    .withBody("OK")));

        given()
            .auth()
            .digest("someone","secret")
        .when()
            .get(AUTHENTICATED_PATH)
        .then()
            .assertThat()
            .statusCode(200)
            .body(is("OK"));

    }

    @Test
    public void givenUrl_whenTestChallengedCertficiateAuthentication_thenCorrect() {
        // Mock any response, checking https
        stubFor(get(urlEqualTo(AUTHENTICATED_PATH))
            .willReturn(aResponse().withStatus(200)
                    .withHeader("Content-Type", APPLICATION_JSON)
                    .withBody("OK")));

        given()
            .auth()
            .certificate(
                this.getClass().getResource(KEYSTORE_JKS).getPath(),
                KEY_STORE_PASSWORD,
                CertificateAuthSettings.certAuthSettings().allowAllHostnames()
            )
        .when()
            // Remark that with https we dont have nice relative paths support
            .get("https://localhost:"+wireMockServer.httpsPort()+AUTHENTICATED_PATH)
        .then()
            .assertThat()
            .statusCode(200)
            .body(is("OK"));

    }

    @Test
    public void givenUrl_whenTestFormAuthentication_thenCorrect() {
        final String formAction = "/login";
        final String username = "username";
        final String secret = "secret";
        final String pwdInputTagName = "pass";
        final String userInputTagName = "user";

        // Mock form auth
        // Expect a first request posting credentials
        stubFor(post(urlEqualTo(formAction))
            .withRequestBody(new ContainsPattern(userInputTagName +"="+ username +"&"+ pwdInputTagName +"="+ secret))
            .willReturn(aResponse().withStatus(200)
                .withHeader("Set-Cookie","sid=xxxx")
                .withBody("OK")));
        stubFor(get(urlEqualTo(AUTHENTICATED_PATH))
            .withHeader("Cookie",new ContainsPattern("sid=xxxx"))
            .willReturn(aResponse().withStatus(200)
                    .withBody("OK")));

        given()
            .auth()
                .form(username, secret, new FormAuthConfig(formAction, userInputTagName, pwdInputTagName))
        .when()
            .get(AUTHENTICATED_PATH)
        .then()
            .assertThat()
            .statusCode(200)
            .body(is("OK"));

    }

    @Test
    public void givenUrl_whenTestOAuth1Authentication_thenCorrect() {
        // Mock OAuth 1.0
        stubFor(get(urlEqualTo(AUTHENTICATED_PATH))
            .withHeader("Authorization", new RegexPattern("OAuth.*myAccessToken.*"))
            .willReturn(aResponse().withStatus(200)
                .withHeader("Content-Type", APPLICATION_JSON)
                .withBody("OK")));

        given()
            .auth()
            .oauth("consumerKey","consumerSecret","myAccessToken","secretToken")
        .when()
            .get(AUTHENTICATED_PATH)
        .then()
            .assertThat()
            .statusCode(200)
            .body(is("OK"));

    }


    @Test
    public void givenUrl_whenTestOAuth2Authentication_thenCorrect() {

        // Mock OAuth 2.0
        final String accessToken = "myAccessToken";
        stubFor(get(urlEqualTo(AUTHENTICATED_PATH))
            .withHeader("Authorization", new ContainsPattern("Bearer "+ accessToken))
            .willReturn(aResponse().withStatus(200)
                .withHeader("Content-Type", APPLICATION_JSON)
                .withBody("OK")));

        given()
            .auth()
            .oauth2(accessToken)
        .when()
            .get("/authenticated")
        .then()
            .assertThat()
            .statusCode(200)
            .body(is("OK"));
    }

    @Test
    public void givenUrl_whenTestCustomAuthentication_thenCorrect() {
        final String customerHeaderName = "AUTH";
        final String authHeaderValue = "authHeaderValue";
        stubFor(get(urlEqualTo(AUTHENTICATED_PATH))
            .withHeader(customerHeaderName, new ContainsPattern(authHeaderValue))
            .willReturn(aResponse().withStatus(200)
                .withHeader("Content-Type", APPLICATION_JSON)
                .withBody("OK")));

        given()
            .filter((requestSpec, responseSpec, ctx) ->
                ctx.next((FilterableRequestSpecification) requestSpec.header(customerHeaderName, authHeaderValue), responseSpec))
        .when()
            .get(AUTHENTICATED_PATH)
        .then()
            .statusCode(200)
            .body(is("OK"));
    }
}
package com.baeldung.restassured;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.hasItems;

public class RestAssured2IntegrationTest {
    private static WireMockServer wireMockServer;

    private static final String EVENTS_PATH = "/odds";
    private static final String APPLICATION_JSON = "application/json";
    private static final String ODDS = getJson();

    @BeforeClass
    public static void before() throws Exception {
        System.out.println("Setting up!");
        final int port = Util.getAvailablePort();
        wireMockServer = new WireMockServer(port);
        wireMockServer.start();
        configureFor("localhost", port);
        RestAssured.port = port;
        stubFor(get(urlEqualTo(EVENTS_PATH)).willReturn(
          aResponse().withStatus(200)
            .withHeader("Content-Type", APPLICATION_JSON)
            .withBody(ODDS)));
        stubFor(post(urlEqualTo("/odds/new"))
            .withRequestBody(containing("{\"price\":5.25,\"status\":1,\"ck\":13.1,\"name\":\"X\"}"))
            .willReturn(aResponse().withStatus(201)));
    }

    @Test
    public void givenUrl_whenVerifiesOddPricesAccuratelyByStatus_thenCorrect() {
        get("/odds").then().body("odds.findAll { it.status > 0 }.price",
          hasItems(5.25f, 1.2f));
    }

    @Test
    public void whenRequestedPost_thenCreated() {
        with().body(new Odd(5.25f, 1, 13.1f, "X"))
            .when()
            .request("POST", "/odds/new")
            .then()
            .statusCode(201);
    }

    private static String getJson() {
        return Util.inputStreamToString(RestAssured2IntegrationTest.class
          .getResourceAsStream("/odds.json"));
    }

    @AfterClass
    public static void after() throws Exception {
        System.out.println("Running: tearDown");
        wireMockServer.stop();
    }
}

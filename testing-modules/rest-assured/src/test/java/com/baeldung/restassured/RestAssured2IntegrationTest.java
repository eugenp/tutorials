package com.baeldung.restassured;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.hasItems;

public class RestAssured2IntegrationTest {
    private static final int PORT = 8084;
    private static WireMockServer wireMockServer = new WireMockServer(PORT);

    private static final String EVENTS_PATH = "/odds";
    private static final String APPLICATION_JSON = "application/json";
    private static final String ODDS = getJson();

    @BeforeClass
    public static void before() throws Exception {
        System.out.println("Setting up!");
        wireMockServer.start();
        configureFor("localhost", PORT);
        RestAssured.port = PORT;
        stubFor(get(urlEqualTo(EVENTS_PATH)).willReturn(
          aResponse().withStatus(200)
            .withHeader("Content-Type", APPLICATION_JSON)
            .withBody(ODDS)));
    }

    @Test
    public void givenUrl_whenVerifiesOddPricesAccuratelyByStatus_thenCorrect() {
        get("/odds").then().body("odds.findAll { it.status > 0 }.price",
          hasItems(5.25f, 1.2f));
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

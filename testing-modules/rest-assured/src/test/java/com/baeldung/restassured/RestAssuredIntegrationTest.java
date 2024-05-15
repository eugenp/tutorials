package com.baeldung.restassured;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.get;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.module.jsv.JsonSchemaValidatorSettings.settings;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.tomakehurst.wiremock.WireMockServer;

import io.restassured.RestAssured;

public class RestAssuredIntegrationTest {
    private static WireMockServer wireMockServer;

    private static final String EVENTS_PATH = "/events?id=390";
    private static final String APPLICATION_JSON = "application/json";
    private static final String GAME_ODDS = getEventJson();

    @BeforeClass
    public static void before() {
        System.out.println("Setting up!");
        final int port = Util.getAvailablePort();
        wireMockServer = new WireMockServer(port);
        wireMockServer.start();
        RestAssured.port = port;
        configureFor("localhost", port);
        stubFor(com.github.tomakehurst.wiremock.client.WireMock.get(urlEqualTo(EVENTS_PATH))
          .willReturn(aResponse().withStatus(200)
            .withHeader("Content-Type", APPLICATION_JSON)
            .withBody(GAME_ODDS)));
    }

    @Test
    public void givenUrl_whenCheckingFloatValuePasses_thenCorrect() {
        get("/events?id=390").then()
          .assertThat()
          .body("odd.ck", equalTo(12.2f));
    }

    @Test
    public void givenUrl_whenSuccessOnGetsResponseAndJsonHasRequiredKV_thenCorrect() {

        get("/events?id=390").then()
          .statusCode(200)
          .assertThat()
          .body("id", equalTo("390"));
    }

    @Test
    public void givenUrl_whenJsonResponseHasArrayWithGivenValuesUnderKey_thenCorrect() {
        get("/events?id=390").then()
          .assertThat()
          .body("odds.price", hasItems("1.30", "5.25", "2.70", "1.20"));
    }

    @Test
    public void givenUrl_whenJsonResponseConformsToSchema_thenCorrect() {

        get("/events?id=390").then()
          .assertThat()
          .body(matchesJsonSchemaInClasspath("event_0.json"));
    }

    @Test
    public void givenUrl_whenValidatesResponseWithInstanceSettings_thenCorrect() {
        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder()
          .setValidationConfiguration(ValidationConfiguration.newBuilder()
            .setDefaultVersion(SchemaVersion.DRAFTV4)
            .freeze())
          .freeze();

        get("/events?id=390").then()
          .assertThat()
          .body(matchesJsonSchemaInClasspath("event_0.json").using(jsonSchemaFactory));
    }

    @Test
    public void givenUrl_whenValidatesResponseWithStaticSettings_thenCorrect() {

        get("/events?id=390").then()
          .assertThat()
          .body(matchesJsonSchemaInClasspath("event_0.json").using(settings().with()
            .checkedValidation(false)));
    }

    @AfterClass
    public static void after() {
        System.out.println("Running: tearDown");
        wireMockServer.stop();
    }

    private static String getEventJson() {
        return Util.inputStreamToString(RestAssuredIntegrationTest.class.getResourceAsStream("/event_0.json"));
    }
}

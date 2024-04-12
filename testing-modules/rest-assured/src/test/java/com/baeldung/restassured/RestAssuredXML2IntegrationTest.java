package com.baeldung.restassured;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.hasItems;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.tomakehurst.wiremock.WireMockServer;

import io.restassured.RestAssured;

public class RestAssuredXML2IntegrationTest {
    private static WireMockServer wireMockServer;

    private static final String EVENTS_PATH = "/teachers";
    private static final String APPLICATION_XML = "application/xml";
    private static final String TEACHERS = getXml();

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
            .withHeader("Content-Type", APPLICATION_XML)
            .withBody(TEACHERS)));
    }

    @Test
    public void givenUrl_whenVerifiesScienceTeacherFromXml_thenCorrect() {
        get("/teachers").then()
          .body("teachers.teacher.find { it.@department == 'science' }.subject", hasItems("math", "physics"));
    }

    private static String getXml() {
        return Util.inputStreamToString(RestAssuredXML2IntegrationTest.class.getResourceAsStream("/teachers.xml"));
    }

    @AfterClass
    public static void after() {
        System.out.println("Running: tearDown");
        wireMockServer.stop();
    }
}

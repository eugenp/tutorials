package com.baeldung.rest.karate;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.intuit.karate.junit4.Karate;
import cucumber.api.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

@RunWith(Karate.class)
@CucumberOptions(features = "classpath:karate")
public class KarateIntegrationTest {

    public static final int PORT_NUMBER = 8097;

    private static final WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.options().port(PORT_NUMBER));

    @BeforeClass
    public static void setUp() throws Exception {
        wireMockServer.start();

        configureFor("localhost", PORT_NUMBER);
        stubFor(get(urlEqualTo("/user/get"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"id\": \"1234\", name: \"John Smith\" }")));
        stubFor(post(urlEqualTo("/user/create"))
                .withHeader("content-type", equalTo("application/json"))
                .withRequestBody(containing("id"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"id\": \"1234\", name: \"John Smith\" }")));

    }

    @AfterClass
    public static void tearDown() throws Exception {
        wireMockServer.stop();
    }

}

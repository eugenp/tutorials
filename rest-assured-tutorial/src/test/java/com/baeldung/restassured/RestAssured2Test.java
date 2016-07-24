package com.baeldung.restassured;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.hamcrest.Matchers.hasItems;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.get;

import com.github.tomakehurst.wiremock.WireMockServer;

public class RestAssured2Test {
	private WireMockServer wireMockServer = new WireMockServer();

	private static final String EVENTS_PATH = "/odds";
	private static final String APPLICATION_JSON = "application/json";
	private static final String ODDS = getJson();

	@Before
	public void before() throws Exception {
		System.out.println("Setting up!");
		wireMockServer.start();
		configureFor("localhost", 8080);
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

		return Util.inputStreamToString(new RestAssured2Test().getClass()
				.getResourceAsStream("/odds.json"));

	}

	@After
	public void after() throws Exception {
		System.out.println("Running: tearDown");
		wireMockServer.stop();
	}

}

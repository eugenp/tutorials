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

public class RestAssuredXML2Test {
	private WireMockServer wireMockServer = new WireMockServer();

	private static final String EVENTS_PATH = "/teachers";
	private static final String APPLICATION_XML = "application/xml";
	private static final String TEACHERS = getXml();

	@Before 
	public void before() throws Exception {
		System.out.println("Setting up!");
				wireMockServer.start();
		configureFor("localhost", 8080);
		stubFor(get(urlEqualTo(EVENTS_PATH)).willReturn(
				aResponse().withStatus(200)
						.withHeader("Content-Type", APPLICATION_XML)
						.withBody(TEACHERS)));
	}
	@Test
	public void givenUrl_whenVerifiesScienceTeacherFromXml_thenCorrect() {
		get("/teachers")
				.then()
				.body("teachers.teacher.find { it.@department == 'science' }.subject",
						hasItems("math", "physics"));
	}
	private static String getXml() {
		
		return Util
				.inputStreamToString(new RestAssuredXML2Test().getClass().getResourceAsStream("/teachers.xml"));
	
}
	@After 
public void after() throws Exception {
	System.out.println("Running: tearDown");
	wireMockServer.stop();
}

}

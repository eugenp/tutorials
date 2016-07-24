package com.baeldung.restassured;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.post;
import static io.restassured.RestAssured.get;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.module.jsv.JsonSchemaValidatorSettings.settings;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.xml.HasXPath.hasXPath;

import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.tomakehurst.wiremock.WireMockServer;
public class RestAssuredXMLTest {
	private WireMockServer wireMockServer = new WireMockServer();
	private static final String EVENTS_PATH = "/employees";
	private static final String APPLICATION_XML = "application/xml";
	private static final String EMPLOYEES = getXml();

	@Before 
	public void before() throws Exception {
		System.out.println("Setting up!");
				wireMockServer.start();
		configureFor("localhost", 8080);
		stubFor(post(urlEqualTo(EVENTS_PATH)).willReturn(
				aResponse().withStatus(200)
						.withHeader("Content-Type", APPLICATION_XML)
						.withBody(EMPLOYEES)));
	}
	@Test
	public void givenUrl_whenXmlResponseValueTestsEqual_thenCorrect() {
		post("/employees").then().assertThat()
				.body("employees.employee.first-name", equalTo("Jane"));
	}

	@Test
	public void givenUrl_whenMultipleXmlValuesTestEqual_thenCorrect() {
		post("/employees").then().assertThat()
				.body("employees.employee.first-name", equalTo("Jane"))
				.body("employees.employee.last-name", equalTo("Daisy"))
				.body("employees.employee.sex", equalTo("f"));
	}

	@Test
	public void givenUrl_whenMultipleXmlValuesTestEqualInShortHand_thenCorrect() {
		post("/employees")
				.then()
				.assertThat()
				.body("employees.employee.first-name", equalTo("Jane"),
						"employees.employee.last-name", equalTo("Daisy"),
						"employees.employee.sex", equalTo("f"));
	}

	@Test
	public void givenUrl_whenValidatesXmlUsingXpath_thenCorrect() {
		post("/employees")
				.then()
				.assertThat()
				.body(hasXPath("/employees/employee/first-name",
						containsString("Ja")));

	}

	@Test
	public void givenUrl_whenValidatesXmlUsingXpath2_thenCorrect() {
		post("/employees")
				.then()
				.assertThat()
				.body(hasXPath("/employees/employee/first-name[text()='Jane']"));

	}


	private static String getXml() {
		
		return Util
				.inputStreamToString(new RestAssuredXMLTest().getClass().getResourceAsStream("/employees.xml"));
	
}
	@After 
public void after() throws Exception {
	System.out.println("Running: tearDown");
	wireMockServer.stop();
}
}

package com.baeldung.restassured;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.post;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.xml.HasXPath.hasXPath;

public class RestAssuredXMLIntegrationTest {
    private static final int PORT = 8081;
    private static WireMockServer wireMockServer = new WireMockServer(PORT);

    private static final String EVENTS_PATH = "/employees";
    private static final String APPLICATION_XML = "application/xml";
    private static final String EMPLOYEES = getXml();

    @BeforeClass
    public static void before() throws Exception {
        System.out.println("Setting up!");
        wireMockServer.start();
        configureFor("localhost", PORT);
        RestAssured.port = PORT;
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
          .inputStreamToString(RestAssuredXMLIntegrationTest.class.getResourceAsStream("/employees.xml"));
    }

    @AfterClass
    public static void after() throws Exception {
        System.out.println("Running: tearDown");
        wireMockServer.stop();
    }
}

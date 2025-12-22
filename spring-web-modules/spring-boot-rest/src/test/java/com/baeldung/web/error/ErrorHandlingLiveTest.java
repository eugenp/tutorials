package com.baeldung.web.error;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.not;
import static com.baeldung.Consts.APPLICATION_PORT;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ErrorHandlingLiveTest {

    private static final String BASE_URL = "http://localhost:" + APPLICATION_PORT + "/spring-boot-rest";
    private static final String EXCEPTION_ENDPOINT = BASE_URL + "/exception";

    private static final String ERROR_RESPONSE_KEY_PATH = "error";
    private static final String XML_RESPONSE_KEY_PATH = "xmlkey";
    private static final String LOCALE_RESPONSE_KEY_PATH = "locale";
    private static final String CAUSE_RESPONSE_KEY_PATH = "cause";
    private static final String RESPONSE_XML_ROOT = "Map";
    private static final String XML_RESPONSE_KEY_XML_PATH = RESPONSE_XML_ROOT + "." + XML_RESPONSE_KEY_PATH;
    private static final String LOCALE_RESPONSE_KEY_XML_PATH = RESPONSE_XML_ROOT + "." + LOCALE_RESPONSE_KEY_PATH;
    private static final String CAUSE_RESPONSE_KEY_XML_PATH = RESPONSE_XML_ROOT + "." + CAUSE_RESPONSE_KEY_PATH;
    private static final String CAUSE_RESPONSE_VALUE = "Error in the faulty controller!";
    private static final String XML_RESPONSE_VALUE = "the XML response is different!";

    @Test
    public void whenRequestingFaultyEndpointAsJson_thenReceiveDefaultResponseWithConfiguredAttrs() {
        given().header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .get(EXCEPTION_ENDPOINT)
            .then()
            .body("$", hasKey(LOCALE_RESPONSE_KEY_PATH))
            .body(CAUSE_RESPONSE_KEY_PATH, is(CAUSE_RESPONSE_VALUE))
            .body("$", not(hasKey(ERROR_RESPONSE_KEY_PATH)))
            .body("$", not(hasKey(XML_RESPONSE_KEY_PATH)));
    }

    @Test
    public void whenRequestingFaultyEndpointAsXml_thenReceiveXmlResponseWithConfiguredAttrs() {
        given().header(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE)
            .get(EXCEPTION_ENDPOINT)
            .then()
            .body(LOCALE_RESPONSE_KEY_XML_PATH, isA(String.class))
            .body(CAUSE_RESPONSE_KEY_XML_PATH, is(CAUSE_RESPONSE_VALUE))
            .body(RESPONSE_XML_ROOT, not(hasKey(ERROR_RESPONSE_KEY_PATH)))
            .body(XML_RESPONSE_KEY_XML_PATH, is(XML_RESPONSE_VALUE));
    }

    @Test
    public void whenRequestingFaultyEndpointAsHtml_thenReceiveWhitelabelPageResponse() throws Exception {
        try (WebClient webClient = new WebClient()) {
            webClient.getOptions()
                .setThrowExceptionOnFailingStatusCode(false);
            HtmlPage page = webClient.getPage(EXCEPTION_ENDPOINT);
            assertThat(page.getBody().asNormalizedText()).contains("Whitelabel Error Page");
        }
    }
}

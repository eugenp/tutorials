package com.baeldung.webclient.manualrequest;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

/**
 * 
 * Note: this Live test requires not only the corresponding application running,
 * but also the Authorization Service and the Resource service located in the Baeldung/spring-security-oauth repo
 * 
 * 
 * @author ger
 *
 */
public class OAuth2ManualRequestLiveTest {

    private static final String BASE_URL = "http://localhost:8080";
    private static final String MANUAL_REQUEST_URI = "/manual-request-oauth";

    private static WebTestClient client;

    @Before
    public void setup() {
        client = WebTestClient.bindToServer()
            .baseUrl(BASE_URL)
            .build();
    }

    @Test
    public void whenRequestingDebugHookOn_thenObtainExpectedMessage() {
        ResponseSpec response = client.get()
            .uri(MANUAL_REQUEST_URI)
            .exchange();
        
        response.expectStatus()
            .isOk()
            .expectBody(String.class)
            .value(Matchers.containsString("Retrieved the resource using a manual approach: {\"id\""));
    }

}

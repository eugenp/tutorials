package com.baeldung.springcloudgateway.webfilters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.assertj.core.api.Condition;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("webfilters")
public class WebFilterFactoriesLiveTest {

    @LocalServerPort
    String port;

    @Autowired
    private WebTestClient client;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void configureClient() {
        client = WebTestClient.bindToServer()
          .baseUrl("http://localhost:" + port)
          .build();
    }

    @Test
    public void whenCallGetThroughGateway_thenAllHTTPRequestHeadersParametersAreSet() throws JSONException {
        String url = "http://localhost:" + port + "/get";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        JSONObject json = new JSONObject(response.getBody());
        JSONObject headers = json.getJSONObject("headers");
        assertThat(headers.getString("My-Header-Good")).isEqualTo("Good");
        assertThat(headers.getString("My-Header-Bad")).isEqualTo("Good");
        assertThat(headers.getString("My-Header-Set")).isEqualTo("Set");
        assertTrue(headers.isNull("My-Header-Remove"));
        JSONObject vars = json.getJSONObject("args");
        assertThat(vars.getString("var")).isEqualTo("good");
    }

    @Test
    public void whenCallHeaderPostThroughGateway_thenAllHTTPResponseHeadersAreSet() {
        ResponseSpec response = client.post()
          .uri("/header/post")
          .exchange();

        response.expectStatus()
          .isOk()
          .expectHeader()
          .valueEquals("My-Header-Rewrite", "password=***")
          .expectHeader()
          .valueEquals("My-Header-Set", "Set")
          .expectHeader()
          .valueEquals("My-Header-Good", "Good")
          .expectHeader()
          .doesNotExist("My-Header-Remove");
    }

    @Test
    public void whenCallPostThroughGateway_thenBodyIsRetrieved() throws JSONException {
        String url = "http://localhost:" + port + "/post";

        HttpEntity<String> entity = new HttpEntity<>("content", new HttpHeaders());

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        JSONObject json = new JSONObject(response.getBody());
        JSONObject data = json.getJSONObject("json");
        assertThat(data.getString("message")).isEqualTo("CONTENT");
    }

    @Test
    public void whenCallPutThroughGateway_thenBodyIsRetrieved() throws JSONException {
        String url = "http://localhost:" + port + "/put";

        HttpEntity<String> entity = new HttpEntity<>("CONTENT", new HttpHeaders());

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        JSONObject json = new JSONObject(response.getBody());
        assertThat(json.getString("message")).isEqualTo("New Body");
    }

    @Test
    public void whenCallDeleteThroughGateway_thenIsUnauthorizedCodeIsSet() {
        ResponseSpec response = client.delete()
          .uri("/delete")
          .exchange();

        response.expectStatus()
          .isUnauthorized();
    }

    @Test
    public void whenCallFakePostThroughGateway_thenIsUnauthorizedCodeIsSet() {
        ResponseSpec response = client.post()
          .uri("/fake/post")
          .exchange();

        response.expectStatus()
          .is3xxRedirection();
    }

    @Test
    public void whenCallStatus504ThroughGateway_thenCircuitBreakerIsExecuted() throws JSONException {
        String url = "http://localhost:" + port + "/status/504";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        JSONObject json = new JSONObject(response.getBody());
        assertThat(json.getString("url")).contains("anything");
    }
}

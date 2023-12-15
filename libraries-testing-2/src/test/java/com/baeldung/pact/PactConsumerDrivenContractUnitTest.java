package com.baeldung.pact;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.model.RequestResponsePact;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "test_provider", hostInterface="localhost")
public class PactConsumerDrivenContractUnitTest {

    @Pact(provider="test_provider", consumer = "test_consumer")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return builder
          .given("test GET")
            .uponReceiving("GET REQUEST")
            .path("/pact")
            .method("GET")
          .willRespondWith()
            .status(200)
            .headers(headers)
            .body("{\"condition\": true, \"name\": \"tom\"}")
          .given("test POST")
            .uponReceiving("POST REQUEST")
            .method("POST")
            .headers(headers)
            .body("{\"name\": \"Michael\"}")
            .path("/pact")
          .willRespondWith()
            .status(201)
          .toPact();
    }

    @Test
    @PactTestFor
    void givenGet_whenSendRequest_shouldReturn200WithProperHeaderAndBody(MockServer mockServer) {
        // when
        ResponseEntity<String> response = new RestTemplate().getForEntity(mockServer.getUrl() + "/pact", String.class);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getHeaders().get("Content-Type").contains("application/json")).isTrue();
        assertThat(response.getBody()).contains("condition", "true", "name", "tom");

        // and
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        String jsonBody = "{\"name\": \"Michael\"}";

        // when
        ResponseEntity<String> postResponse = new RestTemplate().exchange(mockServer.getUrl() + "/pact", HttpMethod.POST, new HttpEntity<>(jsonBody, httpHeaders), String.class);

        // then
        assertThat(postResponse.getStatusCode().value()).isEqualTo(201);
    }

}

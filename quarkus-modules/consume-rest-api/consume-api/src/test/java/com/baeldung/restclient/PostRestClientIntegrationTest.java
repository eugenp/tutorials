package com.baeldung.restclient;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import jakarta.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.baeldung.Post;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class PostRestClientIntegrationTest {

    static WireMockServer wireMockServer;

    @Inject
    @RestClient
    PostRestClient postRestClient;

    @Inject
    ObjectMapper objectMapper;

    @BeforeAll
    static void setup() {
        // Start WireMock server
        wireMockServer = new WireMockServer(WireMockConfiguration.options().dynamicPort());
        wireMockServer.start();

        // Configure WireMock to respond to the API endpoint
        wireMockServer.stubFor(get(urlEqualTo("/posts")).willReturn(aResponse().withHeader("Content-Type", "application/json")
          .withBody("[{\"id\":1,\"title\":\"Post Title 1\",\"description\":\"Post description 1\"}]")));

        // Set system property
        System.setProperty("quarkus.rest-client.post-api.url", wireMockServer.baseUrl());
    }

    @AfterAll
    static void teardown() {
        wireMockServer.stop();
    }

    @Test
    public void whenCallingRestClient_thenPostsAreReturned() throws JsonProcessingException {

        // Call the actual REST client method which will interact with the real API
        List<Post> posts = postRestClient.getAllPosts();

        // Validate the response, assuming we expect some non-empty response from the API
        assertNotNull(posts);
        assertFalse(posts.isEmpty(), "Expected at least one post");
        assertEquals("[{\"id\":1,\"title\":\"Post Title 1\",\"description\":\"Post description 1\"}]", objectMapper.writeValueAsString(posts));
    }

}
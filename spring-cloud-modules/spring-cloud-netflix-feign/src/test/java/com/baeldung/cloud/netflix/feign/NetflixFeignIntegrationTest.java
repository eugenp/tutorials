package com.baeldung.cloud.netflix.feign;

import com.baeldung.cloud.netflix.feign.model.Post;
import com.baeldung.cloud.netflix.feign.service.JSONPlaceHolderService;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.exactly;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"external.api.url=http://localhost:${wiremock.server.port}"})
@AutoConfigureWireMock(port = 0)
public class NetflixFeignIntegrationTest {

    @Autowired
    private JSONPlaceHolderService jsonPlaceHolderService;

    @Before
    public void setup() {
        WireMock.reset();
    }

    @Test
    public void givenExternalApiAvailable_whenGetPosts_thenPostsReturned() {

        WireMock.stubFor(get(urlEqualTo("/posts"))
          .willReturn(okJson("[{ \"userId\": 1, \"id\": 1, \"title\": \"post 1 title\", \"body\": \"post 1 body\" }, "
              + "{ \"userId\": 1, \"id\": 2, \"title\": \"post 2 title\", \"body\": \"post 2 body\" }]")));

        List<Post> posts = jsonPlaceHolderService.getPosts();

        assertEquals(2, posts.size());
        verify(exactly(1), getRequestedFor(urlEqualTo("/posts")));
    }

    @Test
    public void givenExternalApiUnavailable_whenGetPosts_thenEmpty() {

        WireMock.stubFor(get(urlEqualTo("/posts"))
          .willReturn(aResponse().withStatus(500)));

        List<Post> posts = jsonPlaceHolderService.getPosts();

        assertTrue(posts.isEmpty());
        verify(exactly(1), getRequestedFor(urlEqualTo("/posts")));
    }

    @Test
    public void givenExternalApiAvailable_whenGetPostWithId_thenPostExists() {

        WireMock.stubFor(get(urlEqualTo("/posts/1"))
          .willReturn(okJson("{ \"userId\": 1, \"id\": 1, \"title\": \"post 1 title\", \"body\": \"post 1 body\" }")));

        Post post = jsonPlaceHolderService.getPostById(1L);

        assertNotNull(post);
        verify(exactly(1), getRequestedFor(urlEqualTo("/posts/1")));
    }

    @Test
    public void givenExternalApiUnavailable_whenGetPostWithId_thenNull() {

        WireMock.stubFor(get(urlEqualTo("/posts/1"))
          .willReturn(aResponse().withStatus(500)));

        Post post = jsonPlaceHolderService.getPostById(1L);

        assertNull(post);
        verify(exactly(1), getRequestedFor(urlEqualTo("/posts/1")));
    }

    private static ResponseDefinitionBuilder okJson(String json) {
        return aResponse()
          .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
          .withBody(json);
    }
}

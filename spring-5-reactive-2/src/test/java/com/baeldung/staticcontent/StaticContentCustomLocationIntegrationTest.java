package com.baeldung.staticcontent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("assets-custom-location")
public class StaticContentCustomLocationIntegrationTest {

    @Autowired
    private WebTestClient client;

    @Test
    public void whenRequestingHtmlFileFromCustomLocation_thenReturnThisFileAndTextHtmlContentType() throws Exception {
        client.get()
                .uri("/assets/index.html")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE);
    }

    @Test
    public void whenRequestingMissingStaticResource_thenReturnNotFoundStatus() throws Exception {
        client.get()
                .uri("/assets/unknown.png")
                .exchange()
                .expectStatus().isNotFound();
    }

}
package com.baeldung.springsessionmongodb;

import java.util.Base64;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.session.Session;
import org.springframework.session.data.mongo.MongoIndexedSessionRepository;

@SpringBootTest(classes = SpringSessionMongoDBApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringSessionMongoDBIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MongoIndexedSessionRepository repository;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void givenEndpointIsCalledTwiceAndResponseIsReturned_whenMongoDBIsQueriedForCount_thenCountMustBeSame() {
        HttpEntity<String> response = restTemplate
                .exchange("http://localhost:" + port, HttpMethod.GET, null, String.class);
        HttpHeaders headers = response.getHeaders();
        String set_cookie = headers.getFirst(HttpHeaders.SET_COOKIE);

        Session sessionById = repository.findById(getSessionId(set_cookie));

        Assertions.assertEquals(response.getBody(), sessionById.getAttribute("count").toString());
    }

    private String getSessionId(String cookie) {
        return new String(Base64.getDecoder().decode(cookie.split(";")[0].split("=")[1]));
    }

}

package com.baeldung.springsessionmongodb;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.session.data.mongo.MongoIndexedSessionRepository;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Base64;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringSessionMongoDBApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringSessionMongoDBIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MongoIndexedSessionRepository repository;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void givenEndpointIsCalledTwiceAndResponseIsReturned_whenMongoDBIsQueriedForCount_thenCountMustBeSame() {
        HttpEntity<String> response = restTemplate
                .exchange("http://localhost:" + port, HttpMethod.GET, null, String.class);
        HttpHeaders headers = response.getHeaders();
        String set_cookie = headers.getFirst(HttpHeaders.SET_COOKIE);

        Assert.assertEquals(response.getBody(),
                repository.findById(getSessionId(set_cookie)).getAttribute("count").toString());
    }

    private String getSessionId(String cookie) {
        return new String(Base64.getDecoder().decode(cookie.split(";")[0].split("=")[1]));
    }

}

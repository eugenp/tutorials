package com.baeldung.springsessionmongodb;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.session.data.mongo.MongoOperationsSessionRepository;
import org.springframework.test.context.junit4.SpringRunner;
import springsessionmongodb.SpringSessionMongoDBApplication;

import java.util.Base64;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringSessionMongoDBApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SpringSessionMongoDBIntegrationTest {

    @Autowired
    private MongoOperationsSessionRepository repository;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void givenEndpointIsCalledTwiceAndResponseIsReturned_whenMongoDBIsQueriedForCount_thenCountMustBeSame() {
        HttpEntity<String> response = restTemplate.
                exchange("http://localhost:" + 8080, HttpMethod.GET, null, String.class);
        HttpHeaders headers = response.getHeaders();
        String set_cookie = headers.getFirst(HttpHeaders.SET_COOKIE);

        Assert.assertEquals(response.getBody(),
                repository.findById(getSessionId(set_cookie)).getAttribute("count").toString());
    }

    private String getSessionId(String set_cookie) {
        return new String(Base64.getDecoder().decode(set_cookie.split(";")[0].split("=")[1]));
    }

}

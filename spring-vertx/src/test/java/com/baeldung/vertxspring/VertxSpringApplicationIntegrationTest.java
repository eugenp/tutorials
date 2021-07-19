package com.baeldung.vertxspring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class VertxSpringApplicationIntegrationTest {

    @Autowired
    private Integer port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void givenUrl_whenReceivedArticles_thenSuccess() throws InterruptedException {
        ResponseEntity<String> responseEntity = restTemplate
                .getForEntity("http://localhost:" + port + "/api/baeldung/articles", String.class);

        assertEquals(200, responseEntity.getStatusCodeValue());
    }
}



package com.baeldung.springcloudgateway.webfilters;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.RepeatedTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import redis.embedded.RedisServer;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("webfilters")
@TestConfiguration
public class RedisWebFilterFactoriesLiveTest {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisWebFilterFactoriesLiveTest.class);
    
    private RedisServer redisServer;
    
    public RedisWebFilterFactoriesLiveTest() {
    }

    @Before
    public void postConstruct() {
        this.redisServer = new RedisServer(6379);
        redisServer.start();    
    }
    
    @LocalServerPort
    String port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    TestRestTemplate template;
 
    @RepeatedTest(25)
    public void whenCallRedisGetThroughGateway_thenOKStatusOrIsReceived() {
        String url = "http://localhost:" + port + "/redis/get";

        ResponseEntity<String> r = restTemplate.getForEntity(url, String.class);
        // assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        LOGGER.info("Received: status->{}, reason->{}, remaining->{}", 
          r.getStatusCodeValue(), r.getStatusCode().getReasonPhrase(), 
          r.getHeaders().get("X-RateLimit-Remaining"));
    }
    
    @After
    public void preDestroy() {
        redisServer.stop();
    }

}

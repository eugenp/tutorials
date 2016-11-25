package com.baeldung.spring.session.tomcatex;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TomcatControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private int port;
    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;
    private RedisConnection connection;

    @Before
    public void clearRedisData() {
        connection = jedisConnectionFactory.getConnection();
        connection.flushAll();
    }

    @Test
    public void testRedisIsEmpty() {
        Set<byte[]> result = connection.keys("*".getBytes());
        assertEquals(0, result.size());
    }

    @Test
    public void testForbiddenToProtectedEndpoint() {
        ResponseEntity<String> result = restTemplate.getForEntity("/tomcat/admin", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    public void testLoginAddsRedisKey() {
        ResponseEntity<String> result = makeRequest();
        assertEquals("hello tomcat admin", result.getBody()); //login worked

        Set<byte[]> redisResult = connection.keys("*".getBytes());
        assertTrue(redisResult.size() > 0); //redis was populated with data
    }

    @Test //requires that the jetty service is running on port 8081
    public void testFailureAccessingJettyResourceWithTomcatSessionToken() {
        //call the jetty server with the token
        ResponseEntity<String> jettyResult = restTemplate.getForEntity("http://localhost:8081", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, jettyResult.getStatusCode()); //login worked
    }

    @Test //requires that the jetty service is running on port 8081
    public void testAccessingJettyResourceWithTomcatSessionToken() {
        //login to get a session token
        ResponseEntity<String> result = makeRequest();
        assertEquals("hello tomcat admin", result.getBody()); //login worked

        assertTrue(result.getHeaders().containsKey("Set-Cookie"));

        String setCookieValue = result.getHeaders().get("Set-Cookie").get(0);
        String sessionCookie = setCookieValue.split(";")[0];
        String sessionValue = sessionCookie.split("=")[1];

        //Add session token to headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-auth-token", sessionValue);

        //call the jetty server with the token
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> jettyResult = restTemplate.exchange("http://localhost:8081", HttpMethod.GET, request, String.class);
        assertEquals("hello Jetty", jettyResult.getBody()); //login worked

    }

    private ResponseEntity<String> makeRequest() {
        String plainCreds = "admin:password";
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);

        HttpEntity<String> request = new HttpEntity<>(headers);
        return restTemplate.exchange("http://localhost:" + port + "/tomcat/admin", HttpMethod.GET, request, String.class);
    }

}
package com.baeldung.spring.session;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SessionControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
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
    public void testUnauthenticatedCantAccess() {
        ResponseEntity<String> result = restTemplate.getForEntity("/", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    public void testRedisControlsSession() {
        ResponseEntity<String> result = restTemplate.exchange("/", HttpMethod.GET, makeAuthRequest(), String.class);
        assertEquals("hello admin", result.getBody()); //login worked

        Set<byte[]> redisResult = connection.keys("*".getBytes());
        assertTrue(redisResult.size() > 0); //redis is populated with session data

        String sessionCookie = result.getHeaders().get("Set-Cookie").get(0).split(";")[0];
        result = restTemplate.exchange("/", HttpMethod.GET, makeRequestWithCookie(sessionCookie), String.class);
        assertEquals("hello admin", result.getBody()); //access with session works worked

        connection.flushAll(); //clear all keys in redis

        result = restTemplate.exchange("/", HttpMethod.GET, makeRequestWithCookie(sessionCookie), String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());//access denied after sessions are removed in redis

    }

    private HttpEntity<String> makeRequestWithCookie(String sessionCookie) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", sessionCookie);

        return new HttpEntity<>(headers);
    }

    private HttpEntity<String> makeAuthRequest() {
        String plainCreds = "admin:password";
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);

        return new HttpEntity<>(headers);
    }
}
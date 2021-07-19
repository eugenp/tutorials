package com.baeldung;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.baeldung.BooksApiApplication;
import com.baeldung.SessionConfig;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import redis.clients.jedis.Jedis;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { BooksApiApplication.class, SessionConfig.class }, webEnvironment = WebEnvironment.DEFINED_PORT)
public class SessionLiveTest {

    private Jedis jedis;
    private static final String API_URI = "http://localhost:8084/books";

    @Before
    public void setUp() {
        jedis = new Jedis("localhost", 6379);
        jedis.flushAll();
    }

    @Test
    public void whenStart_thenNoSessionsExist() {
        final Set<String> result = jedis.keys("*");
        assertEquals(0, result.size());
    }

    @Test
    public void givenUnauthorizeUser_whenAccessResources_then_unAuthorized() {
        final Response response = RestAssured.get(API_URI);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode());
    }

    @Test
    public void givenAuthorizedUser_whenDeleteSession_thenUnauthorized() {
        // authorize User
        Response response = RestAssured.given().auth().preemptive().basic("user", "userPass").get(API_URI);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        final String sessionCookie = response.getCookie("SESSION");

        // check redis
        final Set<String> redisResult = jedis.keys("*");
        assertTrue(redisResult.size() > 0);

        // login with cookie
        response = RestAssured.given().cookie("SESSION", sessionCookie).get(API_URI);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        // empty redis
        jedis.flushAll();

        // login with cookie again
        response = RestAssured.given().cookie("SESSION", sessionCookie).get(API_URI);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode());
    }
}

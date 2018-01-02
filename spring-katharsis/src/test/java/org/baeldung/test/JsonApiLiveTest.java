package org.baeldung.test;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

/**
 * @author krishan.gandhi
 * The Class JsonApiLiveTest.
 */
public class JsonApiLiveTest {

    /** The Constant URL_PREFIX. */
    private final static String URL_PREFIX = "http://127.0.0.1:8080/spring-katharsis/api/users";

    /**
     * When getting all users then correct.
     */
    @Test
    public void whenGettingAllUsers_thenCorrect() {
        try {
            URL url = new URL(URL_PREFIX);
            final Response response = RestAssured.get(url);
            assertEquals(200, response.statusCode());
            System.out.println(response.asString());
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}

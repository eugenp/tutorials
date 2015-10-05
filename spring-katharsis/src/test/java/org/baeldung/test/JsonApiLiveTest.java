package org.baeldung.test;

import static org.junit.Assert.assertEquals;

import org.baeldung.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class JsonApiLiveTest {

    private final static String URL_PREFIX = "http://localhost:8080/users";

    @Test
    public void whenGettingAllUsers_thenCorrect() {
        final Response response = RestAssured.get(URL_PREFIX);
        assertEquals(200, response.statusCode());
        System.out.println(response.asString());
    }

}

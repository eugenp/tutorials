package org.baeldung.web;

import static org.junit.Assert.assertEquals;

import org.baeldung.persistence.model.MyUser;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

@ActiveProfiles("test")
public class MyUserLiveTest {

    private final MyUser userJohn = new MyUser("john", "doe", "john@doe.com", 11);
    private String URL_PREFIX = "http://localhost:8082/spring-security-rest-full/auth/api/myusers";
    @Test
    public void whenGettingListOfUsers_thenCorrect() {
        final Response response = givenAuth().get(URL_PREFIX);
        final MyUser[] result = response.as(MyUser[].class);
        assertEquals(result.length, 2);
    }

    @Test
    public void givenFirstName_whenGettingListOfUsers_thenCorrect() {
        final Response response = givenAuth().get(URL_PREFIX + "?firstName=john");
        final MyUser[] result = response.as(MyUser[].class);
        assertEquals(result.length, 1);
        assertEquals(result[0].getEmail(), userJohn.getEmail());
    }

    @Test
    public void givenPartialLastName_whenGettingListOfUsers_thenCorrect() {
        final Response response = givenAuth().get(URL_PREFIX + "?lastName=do");
        final MyUser[] result = response.as(MyUser[].class);
        assertEquals(result.length, 2);
    }

    @Test
    public void givenEmail_whenGettingListOfUsers_thenIgnored() {
        final Response response = givenAuth().get(URL_PREFIX + "?email=john");
        final MyUser[] result = response.as(MyUser[].class);
        assertEquals(result.length, 2);
    }

    private RequestSpecification givenAuth() {
        return RestAssured.given().auth().preemptive().basic("user1", "user1Pass");
    }
}

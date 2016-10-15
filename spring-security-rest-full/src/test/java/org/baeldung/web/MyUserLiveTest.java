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

    @Test
    public void whenGettingListOfUsers_thenCorrect() {
        final Response response = givenAuth().get("http://localhost:8080/auth/api/myusers");
        final MyUser[] result = response.as(MyUser[].class);
        assertEquals(result.length, 2);
    }

    @Test
    public void givenFirstName_whenGettingListOfUsers_thenCorrect() {
        final Response response = givenAuth().get("http://localhost:8080/auth/api/myusers?firstName=john");
        final MyUser[] result = response.as(MyUser[].class);
        assertEquals(result.length, 1);
        assertEquals(result[0].getEmail(), userJohn.getEmail());
    }

    @Test
    public void givenPartialLastName_whenGettingListOfUsers_thenCorrect() {
        final Response response = givenAuth().get("http://localhost:8080/auth/api/myusers?lastName=do");
        final MyUser[] result = response.as(MyUser[].class);
        assertEquals(result.length, 2);
    }

    @Test
    public void givenEmail_whenGettingListOfUsers_thenIgnored() {
        final Response response = givenAuth().get("http://localhost:8080/auth/api/myusers?email=john");
        final MyUser[] result = response.as(MyUser[].class);
        assertEquals(result.length, 2);
    }

    private RequestSpecification givenAuth() {
        return RestAssured.given().auth().preemptive().basic("user1", "user1Pass");
    }
}

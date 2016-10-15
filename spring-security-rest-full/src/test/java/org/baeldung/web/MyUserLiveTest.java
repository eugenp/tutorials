package org.baeldung.web;

import static org.junit.Assert.assertEquals;

import org.baeldung.persistence.model.MyUser;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = { ConfigTest.class }, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("test")
public class MyUserLiveTest {

    private final ObjectMapper mapper = new ObjectMapper();
    private final MyUser userJohn = new MyUser("john", "doe", "john@doe.com", 11);
    private final MyUser userTom = new MyUser("tom", "doe", "tom@doe.com", 20);

    // private static boolean setupDataCreated = false;
    //
    // @Before
    // public void setupData() throws JsonProcessingException {
    // if (!setupDataCreated) {
    // withRequestBody(givenAuth(), userJohn).post("http://localhost:8080/auth/myusers");
    // withRequestBody(givenAuth(), userTom).post("http://localhost:8080/auth/myusers");
    // setupDataCreated = true;
    // }
    // }

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

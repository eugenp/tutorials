package org.baeldung.persistence.query;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.baeldung.persistence.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = { ConfigTest.class, PersistenceConfig.class }, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("test")
public class JPASpecificationLiveTest {

    // @Autowired
    // private UserRepository repository;

    private User userJohn;

    private User userTom;

    private final String URL_PREFIX = "http://localhost:8082/spring-security-rest-full/auth/users/spec?search=";

    @Before
    public void init() {
        userJohn = new User();
        userJohn.setFirstName("john");
        userJohn.setLastName("doe");
        userJohn.setEmail("john@doe.com");
        userJohn.setAge(22);
        // repository.save(userJohn);

        userTom = new User();
        userTom.setFirstName("tom");
        userTom.setLastName("doe");
        userTom.setEmail("tom@doe.com");
        userTom.setAge(26);
        // repository.save(userTom);
    }

    @Test
    public void givenFirstAndLastName_whenGettingListOfUsers_thenCorrect() {
        final Response response = givenAuth().get(URL_PREFIX + "firstName:john,lastName:doe");
        final String result = response.body().asString();

        assertTrue(result.contains(userJohn.getEmail()));
        assertFalse(result.contains(userTom.getEmail()));
    }

    @Test
    public void givenFirstNameInverse_whenGettingListOfUsers_thenCorrect() {
        final Response response = givenAuth().get(URL_PREFIX + "firstName!john");
        final String result = response.body().asString();

        assertTrue(result.contains(userTom.getEmail()));
        assertFalse(result.contains(userJohn.getEmail()));
    }

    @Test
    public void givenMinAge_whenGettingListOfUsers_thenCorrect() {
        final Response response = givenAuth().get(URL_PREFIX + "age>25");
        final String result = response.body().asString();

        assertTrue(result.contains(userTom.getEmail()));
        assertFalse(result.contains(userJohn.getEmail()));
    }

    @Test
    public void givenFirstNamePrefix_whenGettingListOfUsers_thenCorrect() {
        final Response response = givenAuth().get(URL_PREFIX + "firstName:jo*");
        final String result = response.body().asString();

        assertTrue(result.contains(userJohn.getEmail()));
        assertFalse(result.contains(userTom.getEmail()));
    }

    @Test
    public void givenFirstNameSuffix_whenGettingListOfUsers_thenCorrect() {
        final Response response = givenAuth().get(URL_PREFIX + "firstName:*n");
        final String result = response.body().asString();

        assertTrue(result.contains(userJohn.getEmail()));
        assertFalse(result.contains(userTom.getEmail()));
    }

    @Test
    public void givenFirstNameSubstring_whenGettingListOfUsers_thenCorrect() {
        final Response response = givenAuth().get(URL_PREFIX + "firstName:*oh*");
        final String result = response.body().asString();

        assertTrue(result.contains(userJohn.getEmail()));
        assertFalse(result.contains(userTom.getEmail()));
    }

    @Test
    public void givenAgeRange_whenGettingListOfUsers_thenCorrect() {
        final Response response = givenAuth().get(URL_PREFIX + "age>20,age<25");
        final String result = response.body().asString();

        assertTrue(result.contains(userJohn.getEmail()));
        assertFalse(result.contains(userTom.getEmail()));
    }

    private final RequestSpecification givenAuth() {
        return RestAssured.given().auth().preemptive().basic("user1", "user1Pass");
    }
}

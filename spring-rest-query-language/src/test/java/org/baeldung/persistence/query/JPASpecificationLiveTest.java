package org.baeldung.persistence.query;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.baeldung.persistence.model.Address;
import org.baeldung.persistence.model.AddressBuilder;
import org.baeldung.persistence.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.format.DateTimeFormatter;

import static java.time.LocalDate.now;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = { ConfigTest.class,
//		PersistenceConfig.class }, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("test")
public class JPASpecificationLiveTest {

    // @Autowired
    // private UserRepository repository;

    private final String URL_PREFIX = "http://localhost:8082/spring-rest-query-language/auth/users/spec?search=";
    private final String EURL_PREFIX = "http://localhost:8082/spring-rest-query-language/auth/users/espec?search=";
    private final String ADV_URL_PREFIX = "http://localhost:8082/spring-rest-query-language/auth/users/spec/adv?search=";
    private User userJohn;
    private User userTom;
    private Address addressJohn;
    private Address addressTom;

    @Before
    public void init() {
        addressJohn = AddressBuilder.anAddress()
                .city("SEATTLE").state("WA").street("MAIN").zipcode("85123").checked(true).dateOfOccupation(now())
                .build();
        addressTom = AddressBuilder.anAddress()
                .city("PHOENIX").state("AZ").street("BOYLSTON").zipcode("98102").checked(false).dateOfOccupation(now().minusYears(2))
                .build();
        userJohn = new User();
        userJohn.setFirstName("john");
        userJohn.setLastName("doe");
        userJohn.setEmail("john@doe.com");
        userJohn.setAge(22);
        userJohn.setAddress(addressJohn);
        // repository.save(userJohn);

        userTom = new User();
        userTom.setFirstName("tom");
        userTom.setLastName("doe");
        userTom.setEmail("tom@doe.com");
        userTom.setAge(26);
        userTom.setAddress(addressTom);
        // repository.save(userTom);
    }

    @Test
    public void givenFirstOrLastName_whenGettingListOfUsers_thenCorrect() {
        final Response response = RestAssured.get(EURL_PREFIX + "firstName:john,'lastName:doe");
        final String result = response.body()
                .asString();
        assertTrue(result.contains(userJohn.getEmail()));
        assertTrue(result.contains(userTom.getEmail()));
    }

    @Test
    public void givenFirstAndLastName_whenGettingListOfUsers_thenCorrect() {
        final Response response = RestAssured.get(URL_PREFIX + "firstName:john,lastName:doe");
        final String result = response.body()
                .asString();

        assertTrue(result.contains(userJohn.getEmail()));
        assertFalse(result.contains(userTom.getEmail()));
    }

    @Test
    public void givenFirstNameInverse_whenGettingListOfUsers_thenCorrect() {
        final Response response = RestAssured.get(URL_PREFIX + "firstName!john");
        final String result = response.body()
                .asString();

        assertTrue(result.contains(userTom.getEmail()));
        assertFalse(result.contains(userJohn.getEmail()));
    }

    @Test
    public void givenMinAge_whenGettingListOfUsers_thenCorrect() {
        final Response response = RestAssured.get(URL_PREFIX + "age>25");
        final String result = response.body()
                .asString();

        assertTrue(result.contains(userTom.getEmail()));
        assertFalse(result.contains(userJohn.getEmail()));
    }

    @Test
    public void givenFirstNamePrefix_whenGettingListOfUsers_thenCorrect() {
        final Response response = RestAssured.get(URL_PREFIX + "firstName:jo*");
        final String result = response.body()
                .asString();

        assertTrue(result.contains(userJohn.getEmail()));
        assertFalse(result.contains(userTom.getEmail()));
    }

    @Test
    public void givenFirstNameSuffix_whenGettingListOfUsers_thenCorrect() {
        final Response response = RestAssured.get(URL_PREFIX + "firstName:*n");
        final String result = response.body()
                .asString();

        assertTrue(result.contains(userJohn.getEmail()));
        assertFalse(result.contains(userTom.getEmail()));
    }

    @Test
    public void givenFirstNameSubstring_whenGettingListOfUsers_thenCorrect() {
        final Response response = RestAssured.get(URL_PREFIX + "firstName:*oh*");
        final String result = response.body()
                .asString();

        assertTrue(result.contains(userJohn.getEmail()));
        assertFalse(result.contains(userTom.getEmail()));
    }

    @Test
    public void givenAgeRange_whenGettingListOfUsers_thenCorrect() {
        final Response response = RestAssured.get(URL_PREFIX + "age>20,age<25");
        final String result = response.body()
                .asString();

        assertTrue(result.contains(userJohn.getEmail()));
        assertFalse(result.contains(userTom.getEmail()));
    }

    @Test
    public void givenFirstNameOrLastNameAndCheckedAddress_whenGettingListOfUsersWithAddressJoin_thenCorrect() {
        final Response response = RestAssured.get(EURL_PREFIX + "firstName:john,'lastName:doe,checked:true");

        final String result = response.body()
                .asString();

        assertTrue(result.contains(userJohn.getEmail()));
        assertFalse(result.contains(userTom.getEmail()));
        assertTrue(result.contains(userJohn.getAddress().getStreet()));
    }

    @Test
    public void givenFirstNameOrLastNameAndDateOfOccupation_whenGettingListOfUsersWithAddressJoin_thenCorrect() {
        final Response response = RestAssured.get(EURL_PREFIX + "firstName:john,'lastName:doe,dateOfOccupation<" + DateTimeFormatter.ofPattern("yyyyMMdd").format(now().minusYears(1)));

        final String result = response.body()
                .asString();

        assertTrue(result.contains(userTom.getEmail()));
        assertFalse(result.contains(userJohn.getEmail()));
        assertTrue(result.contains(userTom.getAddress().getStreet()));
    }

    @Test
    public void givenUnkhnownParameter_whenGettingListOfUsers_thenServerError() {
        RestAssured.get(URL_PREFIX + "firstName1:john").then().statusCode(INTERNAL_SERVER_ERROR.value());
    }

    @Test
    public void givenInvalidParameterValue_whenGettingListOfUsers_thenServerError() {
        RestAssured.get(URL_PREFIX + "dateOfOccupation:20171301").then().statusCode(INTERNAL_SERVER_ERROR.value());
    }

    @Test
    public void givenFirstOrLastName_whenGettingAdvListOfUsers_thenCorrect() {
        final Response response = RestAssured.get(ADV_URL_PREFIX + "firstName:john OR lastName:doe");
        final String result = response.body()
                .asString();
        assertTrue(result.contains(userJohn.getEmail()));
        assertTrue(result.contains(userTom.getEmail()));
    }

    @Test
    public void givenFirstOrFirstNameAndAge_whenGettingAdvListOfUsers_thenCorrect() {
        final Response response = RestAssured.get(ADV_URL_PREFIX + "( firstName:john OR firstName:tom ) AND age>22");
        final String result = response.body()
                .asString();
        assertFalse(result.contains(userJohn.getEmail()));
        assertTrue(result.contains(userTom.getEmail()));
    }

}

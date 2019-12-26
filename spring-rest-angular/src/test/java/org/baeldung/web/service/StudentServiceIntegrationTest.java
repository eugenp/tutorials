package org.baeldung.web.service;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;

import org.apache.commons.lang3.RandomStringUtils;
import org.baeldung.web.main.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class StudentServiceIntegrationTest {
    
    @LocalServerPort
    int port;

    private static final String ENDPOINT = "http://localhost:%s/student/get";

    @Test
    public void givenRequestForStudents_whenPageIsOne_expectContainsNames() {
        given().params("page", "0", "size", "2").get(String.format(ENDPOINT, port)).then().assertThat()
          .body("content.name", hasItems("Bryan", "Ben"));
    }

    @Test
    public void givenRequestForStudents_whenSizeIsTwo_expectTwoItems() {
        given().params("page", "0", "size", "2").get(String.format(ENDPOINT, port)).then().assertThat().body("size", equalTo(2));
    }

    @Test
    public void givenRequestForStudents_whenSizeIsTwo_expectNumberOfElementsTwo() {
        given().params("page", "0", "size", "2").get(String.format(ENDPOINT, port)).then().assertThat().body("numberOfElements", equalTo(2));
    }

    @Test
    public void givenRequestForStudents_whenResourcesAreRetrievedPaged_thenExpect200() {
        given().params("page", "0", "size", "2").get(String.format(ENDPOINT, port)).then().statusCode(200);
    }

    @Test
    public void givenRequestForStudents_whenPageOfResourcesAreRetrievedOutOfBounds_thenExpect500() {
        given().params("page", "1000", "size", "2").get(String.format(ENDPOINT, port)).then().statusCode(500);
    }

    @Test
    public void givenRequestForStudents_whenPageNotValid_thenExpect500() {
        given().params("page", RandomStringUtils.randomNumeric(5), "size", "2").get(String.format(ENDPOINT, port)).then().statusCode(500);
    }

    @Test
    public void givenRequestForStudents_whenPageSizeIsFive_expectFiveItems() {
        given().params("page", "0", "size", "5").get(String.format(ENDPOINT, port)).then().body("content.size()", is(5));
    }

    @Test
    public void givenResourcesExist_whenFirstPageIsRetrieved_thenPageContainsResources() {
        given().params("page", "0", "size", "2").get(String.format(ENDPOINT, port)).then().assertThat().body("first", equalTo(true));
    }
}

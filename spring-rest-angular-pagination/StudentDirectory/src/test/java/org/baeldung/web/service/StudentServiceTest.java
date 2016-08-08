package org.baeldung.web.service;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.util.Assert.isTrue;

import org.apache.commons.lang3.RandomStringUtils;
import org.baeldung.web.main.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:8888")
public class StudentServiceTest {

    private static final String ENDPOINT = "http://localhost:8080/StudentDirectory/student/get";

    @Test
    public void givenRequestForStudents_whenPageIsOne_expectContainsNames() {
        given().params("page", "1", "size", "2").get(ENDPOINT)
                .then()
                .assertThat().body("content.name", hasItems("Bryan", "Ben"));
    }

    @Test
    public void givenRequestForStudents_whenSizeIsTwo_expectTwoItems() {
        given().params("page", "1", "size", "2").get(ENDPOINT)
                .then()
                .assertThat().body("size", equalTo(2));
    }

    @Test
    public void givenRequestForStudents_whenSizeIsTwo_expectNumberOfElementsTwo() {
        given().params("page", "1", "size", "2").get(ENDPOINT)
                .then()
                .assertThat().body("numberOfElements", equalTo(2));
    }

    @Test
    public void givenRequestForStudents_whenResourcesAreRetrievedPaged_thenExpect200() {
        given().params("page", "1", "size", "2").get(ENDPOINT)
                .then()
                .statusCode(200);
    }

    @Test
    public void givenRequestForStudents_whenPageOfResourcesAreRetrievedOutOfBounds_thenExpect500() {
        given().params("page", "1000", "size", "2").get(ENDPOINT)
                .then()
                .statusCode(500);
    }

    @Test
    public void givenRequestForStudents_whenPageNotValid_thenExpect500() {
        given().params("page", RandomStringUtils.randomNumeric(5), "size", "2").get(ENDPOINT)
                .then()
                .statusCode(500);
    }

    @Test
    public void givenResourcesExist_whenFirstPageIsRetrieved_thenPageContainsResources() {
        given().params("page", "1", "size", "2").get(ENDPOINT)
                .then()
                .assertThat().body("first", equalTo(true));
    }

    @Test
    public void givenRequestForStudents_whenPageIsFive_expectFiveItems() {
        given().params("page", "1", "size", "5").get(ENDPOINT)
                .then()
                .body("content.studentId.max()", equalTo("5"));
    }
}

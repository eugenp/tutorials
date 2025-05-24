package com.baeldung.restassured.assertjson;

import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static uk.org.webcompere.modelassert.json.JsonAssertions.assertJson;

class RestAssuredAssertJsonWithModelAssertUnitTest extends WireMockTestBase {

    @Test
    void whenGetBody_thenCanCompareByModelAssert() {
        String body = given()
            .get("/static")
            .then()
            .extract()
            .body()
            .asString();

        assertJson(body)
            .isEqualTo("{\"name\":\"baeldung\",\"type\":\"website\",\"text\":{\"language\":\"english\",\"code\":\"java\"}}");
    }

    @Test
    void whenGetBody_thenCanCompareByModelAssertWithFieldsInDifferentOrder() {
        String body = given()
            .get("/static")
            .then()
            .extract()
            .body()
            .asString();

        assertJson(body)
            .where()
            .keysInAnyOrder()
            .isEqualTo("{\"type\":\"website\",\"name\":\"baeldung\",\"text\":{\"language\":\"english\",\"code\":\"java\"}}");
    }

    @Test
    void whenGetBody_thenCanCompareByModelAssertAgainstFile() {
        String body = given()
            .get("/static")
            .then()
            .extract()
            .body()
            .asString();

        assertJson(body)
            .isEqualTo(new File("src/test/resources/expected-website.json"));
    }

    @Test
    void whenGetBody_thenCanCompareByModelAssertAgainstFileDifferentOrder() {
        String body = given()
            .get("/static")
            .then()
            .extract()
            .body()
            .asString();

        assertJson(body)
            .where()
            .keysInAnyOrder()
            .isEqualTo(new File("src/test/resources/expected-website-different-field-order.json"));
    }

    @Test
    void whenGetBody_thenCanCompareByModelAssertAgainstUnpredictableFieldsByIgnoringExtraFields() {
        String body = given()
            .get("/build")
            .then()
            .extract()
            .body()
            .asString();

        assertJson(body)
            .where()
            .objectContains()
            .isEqualTo("{\"type\":\"website\",\"name\":\"baeldung\",\"text\":{\"language\":\"english\",\"code\":\"java\"}}");
    }

    @Test
    void whenGetBody_thenCanCompareByModelAssertAgainstUnpredictableFields() {
        String body = given()
            .get("/build")
            .then()
            .extract()
            .body()
            .asString();

        assertJson(body)
            .where()
            .keysInAnyOrder()
                .path("build").matches("[0-9a-f-]+")
                .path("timestamp").matches("[0-9:T.-]+")
            .isEqualTo("{\"build\":\"build\",\"timestamp\":\"timestamps\",\"type\":\"website\",\"name\":\"baeldung\",\"text\":{\"language\":\"english\",\"code\":\"java\"}}");
    }

    @Test
    void whenGetBody_thenCanCompareByModelAssertUnpredictableFieldsAgainstFile() {
        String body = given()
            .get("/build")
            .then()
            .extract()
            .body()
            .asString();

        assertJson(body)
            .where()
            .keysInAnyOrder()
            .path("build").matches("[0-9a-f-]+")
            .path("timestamp").matches("[0-9:T.-]+")
            .isEqualTo(new File("src/test/resources/expected-build.json"));
    }
}

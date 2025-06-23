package com.baeldung.restassured.assertjson;

import net.javacrumbs.jsonunit.core.Option;
import org.assertj.core.util.Files;
import org.junit.jupiter.api.Test;


import java.io.File;

import static io.restassured.RestAssured.given;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class RestAssuredAssertJsonWithJsonUnitUnitTest extends WireMockTestBase {

    @Test
    void whenGetBody_thenCanCompareByJsonUnit() {
        String body = given()
            .get("/static")
            .then()
            .extract()
            .body()
            .asString();

        assertThatJson(body)
            .isEqualTo("{\"name\":\"baeldung\",\"type\":\"website\",\"text\":{\"language\":\"english\",\"code\":\"java\"}}");
    }

    @Test
    void whenGetBody_thenCanCompareByJsonUnitWithFieldsInDifferentOrder() {
        String body = given()
            .get("/static")
            .then()
            .extract()
            .body()
            .asString();

        assertThatJson(body)
            .isEqualTo("{\"type\":\"website\",\"name\":\"baeldung\",\"text\":{\"language\":\"english\",\"code\":\"java\"}}");
    }

    @Test
    void whenGetBody_thenCanCompareByJsonUnitAgainstFile() {
        String body = given()
            .get("/static")
            .then()
            .extract()
            .body()
            .asString();

        assertThatJson(body)
            .isEqualTo(Files.contentOf(new File("src/test/resources/expected-website.json"), "UTF-8"));
    }

    @Test
    void whenGetBody_thenCanCompareByJsonUnitAgainstFileDifferentOrder() {
        String body = given()
            .get("/static")
            .then()
            .extract()
            .body()
            .asString();

        assertThatJson(body)
            .isEqualTo(Files.contentOf(new File("src/test/resources/expected-website-different-field-order.json"), "UTF-8"));
    }

    @Test
    void whenGetBody_thenCanCompareByJsonUnitAgainstUnpredictableFieldsByIgnoringExtraFields() {
        String body = given()
            .get("/build")
            .then()
            .extract()
            .body()
            .asString();

        assertThatJson(body)
            .when(Option.IGNORING_EXTRA_FIELDS)
            .isEqualTo("{\"type\":\"website\",\"name\":\"baeldung\",\"text\":{\"language\":\"english\",\"code\":\"java\"}}");
    }

    @Test
    void whenGetBody_thenCanCompareByJsonUnitAgainstUnpredictableFields() {
        String body = given()
            .get("/build")
            .then()
            .extract()
            .body()
            .asString();

        assertThatJson(body)
            .isEqualTo("{\"build\":\"${json-unit.regex}[0-9a-f-]+\",\"timestamp\":\"${json-unit.any-string}\",\"type\":\"website\",\"name\":\"baeldung\",\"text\":{\"language\":\"english\",\"code\":\"java\"}}");
    }
}

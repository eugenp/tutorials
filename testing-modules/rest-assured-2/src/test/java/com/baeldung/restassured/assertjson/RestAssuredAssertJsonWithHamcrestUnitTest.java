package com.baeldung.restassured.assertjson;

import net.javacrumbs.jsonunit.core.Option;
import org.assertj.core.util.Files;
import org.junit.jupiter.api.Test;
import java.io.File;

import static io.restassured.RestAssured.given;
import static uk.co.datumedge.hamcrest.json.SameJSONAs.sameJSONAs;

import static net.javacrumbs.jsonunit.JsonMatchers.*;
import static uk.org.webcompere.modelassert.json.JsonAssertions.json;

class RestAssuredAssertJsonWithHamcrestUnitTest extends WireMockTestBase {

    @Test
    void whenGetBody_thenCanCompareChangeableFieldsByJsonAssertAgainstFileIgnoringAdditionalFields() {
        given()
            .get("/build")
            .then()
            .body(sameJSONAs(Files.contentOf(new File("src/test/resources/expected-website.json"), "UTF-8")).allowingExtraUnexpectedFields());
    }

    @Test
    void whenGetBody_thenCanCompareChangeableFieldsByJsonUnitAgainstFileIgnoringAdditionalFields() {
        given()
            .get("/build")
            .then()
            .body(jsonEquals(Files.contentOf(new File("src/test/resources/expected-website.json"), "UTF-8")).when(Option.IGNORING_EXTRA_FIELDS));
    }

    @Test
    void whenGetBody_thenCanCompareChangeableFieldsByModelAssertionAgainstFileWithPatterns() {
        given()
                .get("/build")
                .then()
                .body(json().where()
                    .keysInAnyOrder()
                    .path("build").matches("[0-9a-f-]+")
                    .path("timestamp").matches("[0-9:T.-]+")
                    .isEqualTo(new File("src/test/resources/expected-build.json")));
    }

}

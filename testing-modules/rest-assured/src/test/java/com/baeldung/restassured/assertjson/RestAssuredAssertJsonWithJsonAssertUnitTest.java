package com.baeldung.restassured.assertjson;

import org.assertj.core.util.Files;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.RegularExpressionValueMatcher;
import org.skyscreamer.jsonassert.comparator.CustomComparator;

import java.io.File;

import static io.restassured.RestAssured.given;

class RestAssuredAssertJsonWithJsonAssertUnitTest extends WireMockTestBase {

    @Test
    void whenGetBody_thenCanCompareByJsonAssert() throws Exception {
        String body = given()
            .get("/static")
            .then()
            .extract()
            .body()
            .asString();

        JSONAssert.assertEquals("{\"name\":\"baeldung\",\"type\":\"website\",\"text\":{\"language\":\"english\",\"code\":\"java\"}}", body, JSONCompareMode.STRICT);
    }

    @Test
    void whenGetBody_thenCanCompareByJsonAssertWithFieldsInDifferentOrder() throws Exception {
        String body = given()
            .get("/static")
            .then()
            .extract()
            .body()
            .asString();

        JSONAssert.assertEquals("{\"type\":\"website\",\"name\":\"baeldung\",\"text\":{\"language\":\"english\",\"code\":\"java\"}}", body, JSONCompareMode.STRICT);
    }

    @Test
    void whenGetBody_thenCanCompareByJsonAssertAgainstFile() throws Exception {
        String body = given()
            .get("/static")
            .then()
            .extract()
            .body()
            .asString();

        JSONAssert.assertEquals(Files.contentOf(new File("src/test/resources/expected-website.json"), "UTF-8"), body, JSONCompareMode.STRICT);
    }

    @Test
    void whenGetBody_thenCanCompareByJsonAssertAgainstFileDifferentOrder() throws Exception {
        String body = given()
            .get("/static")
            .then()
            .extract()
            .body()
            .asString();

        JSONAssert.assertEquals(Files.contentOf(new File("src/test/resources/expected-website-different-field-order.json"), "UTF-8"), body, JSONCompareMode.STRICT);
    }

    @Disabled("This doesn't work because the file contains fields that change every time")
    @Test
    void whenGetBody_thenCannotCompareChangeableFieldsByJsonAssertAgainstFile() throws Exception {
        String body = given()
            .get("/build")
            .then()
            .extract()
            .body()
            .asString();

        JSONAssert.assertEquals(Files.contentOf(new File("src/test/resources/expected-build.json"), "UTF-8"), body, JSONCompareMode.STRICT);
    }

    @Test
    void whenGetBody_thenCanCompareChangeableFieldsByIgnoringExtraFields() throws Exception {
        String body = given()
                .get("/build")
                .then()
                .extract()
                .body()
                .asString();

        JSONAssert.assertEquals(Files.contentOf(new File("src/test/resources/expected-website.json"), "UTF-8"), body, JSONCompareMode.LENIENT);
    }

    @Test
    void whenGetBody_thenCanCompareChangeableFieldsByJsonAssertAgainstFile() throws Exception {
        String body = given()
            .get("/build")
            .then()
            .extract()
            .body()
            .asString();

        JSONAssert.assertEquals(Files.contentOf(new File("src/test/resources/expected-build.json"), "UTF-8"), body,
            new CustomComparator(JSONCompareMode.STRICT,
                new Customization("build",
                    new RegularExpressionValueMatcher<>("[0-9a-f-]+")),
                new Customization("timestamp",
                    new RegularExpressionValueMatcher<>(".+"))));
    }
}

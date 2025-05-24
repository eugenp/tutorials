package com.baeldung.restassured.assertjson;

import com.baeldung.restassured.assertjson.WebsitePojo.WebsiteText;
import org.assertj.core.util.Files;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;

class RestAssuredAssertJsonUnitTest extends WireMockTestBase {

    @Test
    void whenGetBody_thenCanCompareByFields() {
        given()
            .get("/static")
            .then()
            .body("name", equalTo("baeldung"))
            .body("type", equalTo("website"))
            .body("text.code", equalTo("java"))
            .body("text.language", equalTo("english"));
    }

    @Test
    void whenGetBody_thenCanCompareByWholeString() {
        String body = given()
            .get("/static")
            .then()
            .extract()
            .body()
            .asString();

        assertThat(body)
            .isEqualTo("{\"name\":\"baeldung\",\"type\":\"website\",\"text\":{\"language\":\"english\",\"code\":\"java\"}}");
    }

    @Test
    void whenGetBody_thenCompareByWholeStringIsSensitiveToFieldOrder() {
        String body = given()
                .get("/static")
                .then()
                .extract()
                .body()
                .asString();

        assertThat(body)
                .isNotEqualTo("{\"type\":\"website\",\"name\":\"baeldung\",\"text\":{\"language\":\"english\",\"code\":\"java\"}}");
    }

    @Test
    void whenGetBody_thenCanCompareByPojo() {
        WebsitePojo body = given()
                .get("/static")
                .then()
                .extract()
                .body()
                .as(WebsitePojo.class);

        assertThat(body)
            .isEqualTo(new WebsitePojo("baeldung", "website", new WebsiteText("english", "java")));
    }

    @Disabled("This test cannot predict UUID and timestamp")
    @Test
    void whenGetBody_thenCannotEasilyHandleUnpredictableValues() {
        String body = given()
                .get("/build")
                .then()
                .extract()
                .body()
                .asString();

        assertThat(body)
                .isEqualTo("{\"name\":\"baeldung\",\"type\":\"website\",\"text\":{\"language\":\"english\",\"code\":\"java\"}}");
    }

    @Disabled("Requires file to be in the exact correct format")
    @Test
    void whenGetBody_thenCannotEasilyCompareWithFile() {
        String body = given()
                .get("/static")
                .then()
                .extract()
                .body()
                .asString();

        assertThat(body)
                .isEqualTo(Files.contentOf(new File("src/test/resources/expected-website.json"), "UTF-8"));
    }
}

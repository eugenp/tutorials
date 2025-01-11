package com.baeldung.quarkus.langchain4j;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
@Disabled //Needs chatgpt.com API key
class ChatAPIResourceLiveTest {

    @Test
    void testChatAPI() {
        String id = UUID.randomUUID().toString();

        RestAssured.given()
                .queryParam("q", "What is Quarkus?")
                .queryParam("id", id)
                .when()
                .contentType(ContentType.JSON)
                    .post("/chat")
                .then()
                .statusCode(200)
                .body("message", containsString("Quarkus is a Kubernetes"));
    }

}

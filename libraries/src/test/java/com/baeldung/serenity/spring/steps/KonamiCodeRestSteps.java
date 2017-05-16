package com.baeldung.serenity.spring.steps;

import io.restassured.module.mockmvc.response.MockMvcResponse;
import net.thucydides.core.annotations.Step;

import java.io.UnsupportedEncodingException;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author aiet
 */
public class KonamiCodeRestSteps {

    MockMvcResponse mockMvcResponse;
    String cheatcode;

    @Step("get the classic cheat code")
    public void givenClassicCheatCode() throws UnsupportedEncodingException {
        cheatcode = given()
          .when()
          .get("/konamicode/classic")
          .mvcResult()
          .getResponse()
          .getContentAsString();
    }

    @Step("check if the cheat code works")
    public void whenCheat() {
        mockMvcResponse = given()
          .queryParam("cheatcode", cheatcode)
          .when()
          .get("/konamicode/cheatable");
    }

    @Step("classic cheat code matches")
    public void thenClassicCodeCanDoTheTrick() {
        mockMvcResponse
          .then()
          .statusCode(200)
          .body(equalTo("true"));
    }

    @Step("all stage cleared")
    public void stageCleared() {
        given()
          .queryParam("action", "clear")
          .when()
          .put("/konamicode/stages");
        given()
          .when()
          .get("/konamicode/stages")
          .then()
          .body(equalTo("0"));
    }

    @Step("one more stage to play")
    public void thenMoreStages() {
        given()
          .when()
          .get("/konamicode/stages")
          .then()
          .body(equalTo("1"));
    }

}


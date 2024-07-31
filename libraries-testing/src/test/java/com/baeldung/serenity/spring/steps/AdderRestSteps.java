package com.baeldung.serenity.spring.steps;

import io.restassured.module.mockmvc.response.MockMvcResponse;
import net.thucydides.core.annotations.Step;

import java.io.UnsupportedEncodingException;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author aiet
 */
public class AdderRestSteps {

    private MockMvcResponse mockMvcResponse;
    private int currentNum;

    @Step("get the current number")
    public void givenCurrentNumber() throws UnsupportedEncodingException {
        currentNum = Integer.valueOf(given().when().get("/adder/current").mvcResult().getResponse().getContentAsString());
    }

    @Step("adding {0}")
    public void whenAddNumber(int num) {
        mockMvcResponse = given().queryParam("num", num).when().post("/adder");
        currentNum += num;
    }

    @Step("got the sum")
    public void thenSummedUp() {
        mockMvcResponse.then().statusCode(200).body(equalTo(currentNum + ""));
    }

}

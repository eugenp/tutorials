package com.baeldung.quarkus.micrometer;

import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.containsString;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class PalindromeResourceIntegrationTest {

    @Test
    void givenMicrometerApp_whenCallingEndpoint_thenGaugeIsUpdated() {
        when().get("palindrome/clearmetrics").then().statusCode(200);
        when().get("/palindrome/check/radar").then().statusCode(200);
        when().get("/palindrome/check/article").then().statusCode(200);
        when().get("/palindrome/check/level").then().statusCode(200);
        when().get("/q/metrics").then().statusCode(200)
          .body(containsString(
            "palindrome_list_size 3.0"));
        when().delete("/palindrome/empty-list").then().statusCode(204);
        when().get("/palindrome/check/radar").then().statusCode(200);
        when().get("/q/metrics").then().statusCode(200)
          .body(containsString(
            "palindrome_list_size 1.0"));
    }

    @Test
    void givenMicrometerApp_whenCallingEndpoint_thenCounterIsIncremented() {
        when().get("palindrome/clearmetrics").then().statusCode(200);
        when().get("/palindrome/check/article").then().statusCode(200);
        when().get("/palindrome/check/level").then().statusCode(200);

        when().get("/q/metrics").then().statusCode(200)
          .body(containsString(
            "palindrome_counter_total 2"));
    }

    @Test
    void givenMicrometerApp_whenCallingEndpoint_thenTimersAreUpdated() {
        when().get("palindrome/clearmetrics").then().statusCode(200);
        when().get("/palindrome/check/level").then().statusCode(200);
        when().get("/q/metrics").then().statusCode(200)
          .body(containsString(
            "palindrome_timer_seconds_sum"))
          .body(containsString(
            "palindrome_timer_seconds_max"))
          .body(containsString(
            "palindrome_timer_seconds_count 1.0"));
        when().get("/palindrome/check/article").then().statusCode(200);
        when().get("/q/metrics").then().statusCode(200)
          .body(containsString(
            "palindrome_timer_seconds_count 2.0"));
    }

}

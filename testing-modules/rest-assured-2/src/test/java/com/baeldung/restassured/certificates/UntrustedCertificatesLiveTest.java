package com.baeldung.restassured.certificates;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static io.restassured.config.SSLConfig.sslConfig;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;

class UntrustedCertificatesLiveTest {
    private static final String TEST_URL = "https://self-signed.badssl.com";

    @Test
    @Disabled
    void whenCallingUntrustedCertificate_thenTheTestFails() {
        given()
            .baseUri(TEST_URL)
            .when()
            .get("/")
            .then()
            .statusCode(200);
    }

    @Test
    void whenUsingRelaxedHTTPSValidation_thenTheTestPasses() {
        given()
            .relaxedHTTPSValidation()
            .baseUri(TEST_URL)
            .when()
            .get("/")
            .then()
            .statusCode(200);
    }

    @Test
    void whenTheCertificateIsTrusted_thenTheTestPasses() {
        given()
            .config(config()
                .sslConfig(sslConfig()
                    .trustStore("/badssl.jks", "changeit")))
            .baseUri(TEST_URL)
            .when()
            .get("/")
            .then()
            .statusCode(200);
    }

    @Test
    void whenTheCertificateIsTrustedGlobally_thenTheTestPasses() {
        RestAssuredConfig oldConfig = RestAssured.config;

        try {
            RestAssured.config = RestAssured.config()
                .sslConfig(SSLConfig.sslConfig()
                    .trustStore("/badssl.jks", "changeit"));

            given()
                .baseUri(TEST_URL)
                .when()
                .get("/")
                .then()
                .statusCode(200);
        } finally {
            RestAssured.config = oldConfig;
        }
    }
}

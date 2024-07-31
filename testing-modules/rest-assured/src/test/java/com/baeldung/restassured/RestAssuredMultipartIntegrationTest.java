package com.baeldung.restassured;

import static com.github.tomakehurst.wiremock.client.WireMock.aMultipart;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.matching.MultipartValuePatternBuilder;

import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.specification.MultiPartSpecification;

class RestAssuredMultipartIntegrationTest {

    private WireMockServer wireMockServer;

    @BeforeEach
    void startServer() {
        int port = Util.getAvailablePort();
        wireMockServer = new WireMockServer(port);
        wireMockServer.start();
        configureFor("localhost", port);
        RestAssured.port = port;
    }

    @AfterEach
    void stopServer() {
        wireMockServer.stop();
    }

    @Test
    void whenUploadOneFile_ThenSuccess() throws IOException {
        stubFor(post(urlEqualTo("/upload")).withHeader("Content-Type", containing("multipart/form-data"))
          .withRequestBody(containing("file"))
          .withRequestBody(containing(getFileContent("baeldung.txt")))
          .willReturn(aResponse().withStatus(200)));

        given().multiPart("file", getFile("baeldung.txt"))
          .when()
          .post("/upload")
          .then()
          .statusCode(200);
    }

    @Test
    void whenUploadTwoFiles_ThenSuccess() throws IOException {
        stubFor(post(urlEqualTo("/upload")).withHeader("Content-Type", containing("multipart/form-data"))
          .withRequestBody(containing(getFileContent("baeldung.txt")))
          .withRequestBody(containing(getFileContent("helloworld.txt")))
          .willReturn(aResponse().withStatus(200)));

        given().multiPart("file", getFile("baeldung.txt"))
          .multiPart("helloworld", getFile("helloworld.txt"))
          .when()
          .post("/upload")
          .then()
          .statusCode(200);
    }

    @Test
    void whenBuildingMultipartSpecification_ThenSuccess() {
        MultipartValuePatternBuilder multipartValuePatternBuilder = aMultipart().withName("file")
          .withHeader("Content-Disposition", containing("file.txt"))
          .withBody(equalTo("File content"))
          .withHeader("Content-Type", containing("text/plain"));

        stubFor(post(urlEqualTo("/upload")).withMultipartRequestBody(multipartValuePatternBuilder)
          .willReturn(aResponse().withStatus(200)));

        MultiPartSpecification multiPartSpecification = new MultiPartSpecBuilder("File content".getBytes()).fileName("file.txt")
          .controlName("file")
          .mimeType("text/plain")
          .build();

        given().multiPart(multiPartSpecification)
          .when()
          .post("/upload")
          .then()
          .statusCode(200);
    }

    private String getFileContent(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(getFile(fileName).getPath())));
    }

    private File getFile(String fileName) throws IOException {
        return new ClassPathResource(fileName).getFile();
    }

}

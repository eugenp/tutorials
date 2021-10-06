package com.account.service.api;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Tag("integration-test")
abstract class CommonFunctionalTest {

  @LocalServerPort
  private int serverPort;

  com.jayway.restassured.specification.RequestSpecification requestSpecification() {
    return RestAssured
      .given()
      .log().all()
      .port(serverPort)
      .accept(ContentType.JSON)
      .contentType(ContentType.JSON);
  }

}

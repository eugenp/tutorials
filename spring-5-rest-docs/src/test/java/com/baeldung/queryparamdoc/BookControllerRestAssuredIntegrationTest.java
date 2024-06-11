package com.baeldung.queryparamdoc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.restassured.RestAssuredRestDocumentation;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@AutoConfigureWebMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class BookControllerRestAssuredIntegrationTest {

    private RequestSpecification spec;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation, @LocalServerPort int port) {
        this.spec = new RequestSpecBuilder().addFilter(RestAssuredRestDocumentation.documentationConfiguration(restDocumentation))
          .setPort(port)
          .build();
    }

    @Test
    void smokeTest() {
        assertThat(spec).isNotNull();
    }

    @Test
    @WithMockUser
    void givenEndpoint_whenSendGetRequest_thenSuccessfulResponse() {
        RestAssured.given(this.spec).filter(RestAssuredRestDocumentation.document("users", queryParameters(
            parameterWithName("page").description("The page to retrieve"))))
          .when().get("/books?page=2")
          .then().assertThat().statusCode(is(200));
    }
}
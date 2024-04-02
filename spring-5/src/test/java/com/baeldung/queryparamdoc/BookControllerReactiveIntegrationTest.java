package com.baeldung.queryparamdoc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebFluxTest
class BookControllerReactiveIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    public void setUp(ApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.webTestClient = WebTestClient.bindToApplicationContext(webApplicationContext)
          .configureClient()
          .filter(documentationConfiguration(restDocumentation))
          .build();
    }

    @Test
    void smokeTest() {
        assertThat(webTestClient).isNotNull();
    }

    @Test
    @WithMockUser
    void givenEndpoint_whenSendGetRequest_thenSuccessfulResponse() {
        webTestClient.get().uri("/books?page=2")
          .exchange().expectStatus().isOk().expectBody()
          .consumeWith(document("books",
            queryParameters(parameterWithName("page").description("The page to retrieve"))));
    }

    @TestConfiguration
    public static class TestConfig {

        @Bean
        BookService bookService() {
            return new BookService();
        }
    }

}

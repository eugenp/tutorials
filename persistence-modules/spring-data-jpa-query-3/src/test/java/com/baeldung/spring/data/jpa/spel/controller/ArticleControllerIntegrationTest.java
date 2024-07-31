package com.baeldung.spring.data.jpa.spel.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.baeldung.spring.data.jpa.spel.NewsApplication;
import com.baeldung.spring.data.jpa.spel.entity.Article;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(classes = NewsApplication.class,
    webEnvironment = WebEnvironment.RANDOM_PORT,
    properties = {
        "spring.jpa.show-sql=true",
        "spring.jpa.generate-ddl=true",
        "spring.jpa.defer-datasource-initialization=true",
        "spring.sql.init.data-locations=classpath:articles-dml.sql"
    })
class ArticleControllerIntegrationTest {

    @Autowired
    private ArticleController articleController;
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void whenApplicationStartBeansArePresent() {
        assertNotNull(articleController);
        assertNotNull(webTestClient);
    }

    @ParameterizedTest
    @CsvSource({"eng,2", "fr,2", "esp,2", "deu, 2", "jp,0"})
    void whenAskForNewsGetAllNewsInSpecificLanguageBasedOnLocale(String language, int expectedResultSize) {
        webTestClient.get().uri("/articles?locale=" + language)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(Article.class)
            .hasSize(expectedResultSize);
    }


}
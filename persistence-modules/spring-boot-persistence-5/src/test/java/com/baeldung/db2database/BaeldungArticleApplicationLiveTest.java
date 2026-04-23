package com.baeldung.db2database;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.net.URI;
import static org.assertj.core.api.Assertions.assertThat;
import com.baeldung.db2database.entity.Article;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("db2")
class BaeldungArticleApplicationLiveTest {

    @Autowired
    TestRestTemplate restTemplate;


    @Test
    void givenNewArticleObject_whenMakingAPostRequest_thenReturnCreated() {
        Article article = new Article();
        article.setTitle("Introduction to Java");
        article.setAuthor("Baeldung");
        article.setBody("Java is a programming language created by James Gosling");
        ResponseEntity<Article> createResponse = restTemplate.postForEntity("/create-article", article, Article.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI locationOfNewArticle = createResponse.getHeaders().getLocation();
        ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewArticle, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
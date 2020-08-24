package com.baeldung.dynamicproperties;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("pg")
@ExtendWith(PostgreSQLExtension.class)
public class ArticleTestFixtureLiveTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    void givenAnArticle_whenPersisted_thenShouldBeAbleToReadIt() {
        Article article = new Article();
        article.setTitle("A Guide to @DynamicPropertySource in Spring");
        article.setContent("Today's applications...");

        articleRepository.save(article);
        Article persisted = articleRepository.findAll().get(0);
        assertThat(persisted.getId()).isNotNull();
        assertThat(persisted.getTitle()).isEqualTo("A Guide to @DynamicPropertySource in Spring");
        assertThat(persisted.getContent()).isEqualTo("Today's applications...");
    }
}

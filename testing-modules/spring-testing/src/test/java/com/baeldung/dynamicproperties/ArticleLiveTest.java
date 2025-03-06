package com.baeldung.dynamicproperties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@ActiveProfiles("pg")
public class ArticleLiveTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:11")
      .withDatabaseName("prop")
      .withUsername("postgres")
      .withPassword("pass")
      .withExposedPorts(5432);

    @Autowired
    private ArticleRepository articleRepository;

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",
          () -> String.format("jdbc:postgresql://localhost:%d/prop", postgres.getFirstMappedPort()));
        registry.add("spring.datasource.username", () -> "postgres");
        registry.add("spring.datasource.password", () -> "pass");
    }

    @Test
    void givenAnArticle_whenPersisted_thenCanBeFoundInTheDb() {
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

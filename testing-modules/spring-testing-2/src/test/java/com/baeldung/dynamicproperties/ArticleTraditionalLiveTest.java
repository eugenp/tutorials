package com.baeldung.dynamicproperties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@ActiveProfiles("pg")
@ContextConfiguration(initializers = ArticleTraditionalLiveTest.EnvInitializer.class)
class ArticleTraditionalLiveTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:11")
      .withDatabaseName("prop")
      .withUsername("postgres")
      .withPassword("pass")
      .withExposedPorts(5432);

    static class EnvInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
              String.format("spring.datasource.url=jdbc:postgresql://localhost:%d/prop", postgres.getFirstMappedPort()),
              "spring.datasource.username=postgres",
              "spring.datasource.password=pass"
            ).applyTo(applicationContext);
        }
    }

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

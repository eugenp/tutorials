package com.baeldung.hexagonal;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.hexagonal.controller.ArticleController;
import com.baeldung.hexagonal.dto.ArticleDto;

@SpringBootTest
class ApplicationIntegrationTest {

    @Autowired
    ArticleController controller;

    @Test
    void whenAnArticleIsSaved_thenWeCanReadArticleById() {
        // given
        ArticleDto request = new ArticleDto(null, "Learn JUnit", "<div> ... </div>", "learn-junit");
        Long id = controller.createArticle(request).id();
        assertThat(id).isNotNull();

        // when
        ArticleDto savedArticle = controller.readArticle(id).getBody();

        // then
        assertThat(savedArticle).hasFieldOrProperty("id")
            .hasFieldOrPropertyWithValue("name", "Learn JUnit")
            .hasFieldOrPropertyWithValue("content", "<div> ... </div>")
            .hasFieldOrPropertyWithValue("slug", "learn-junit");
    }

}

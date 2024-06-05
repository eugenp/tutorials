package com.baeldung.hexagonal.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.baeldung.hexagonal.persistence.repository.ArticleRepository;

@SpringBootTest
class ArticleServiceTest {

    @MockBean
    ArticleRepository articleRepository;
    @MockBean
    CommentService commentService;
    @MockBean
    RecommendationService recommendationService;
    @MockBean
    UserService userService;
    @Autowired
    ArticleService articleService;

    @Test
    void shouldAllowAuthorToPublishArticle() {
        // ...
    }

}
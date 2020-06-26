package com.baeldung.hexagon.service;

import com.baeldung.hexagon.domain.Article;
import com.baeldung.hexagon.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ArticleServiceTest {

    @Mock
    private ArticleRepository repository;

    @Mock
    private ArticleService.Properties properties;

    @InjectMocks
    private ArticleService service;

    private Article popularArticle;
    private Article notPopularArticle;


    @PostConstruct
    void init() {
        popularArticle = new Article(1,"Popular Article", new Date(),20000L);
        notPopularArticle = new Article(1,"Not popular Article", new Date(),9000L);

        when(properties.getPopularityThreshold()).thenReturn(10000L);
    }

    @Test
    public void whenVisitsGreaterThanThreshold_thenArticleIsPopular() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.of(popularArticle));
        assertTrue(service.isPopular(1));
    }

    @Test
    public void whenVisitsLessThanThreshold_thenArticleIsNotPopular() {
        when(repository.findById(any(Integer.class))).thenReturn(Optional.of(notPopularArticle));
        assertFalse(service.isPopular(1));
    }
}

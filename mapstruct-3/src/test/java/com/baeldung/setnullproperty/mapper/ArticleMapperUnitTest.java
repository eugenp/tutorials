package com.baeldung.setnullproperty.mapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.baeldung.setnullproperty.dto.ArticleDTO;
import com.baeldung.setnullproperty.dto.WeeklyNewsDTO;
import com.baeldung.setnullproperty.entity.Article;
import com.baeldung.setnullproperty.entity.Reviewable;
import com.baeldung.setnullproperty.entity.WeeklyNews;

class ArticleMapperUnitTest {

    private ArticleMapper articleMapper;
    private ReviewableMapper reviewableMapper;

    @BeforeEach
    void setUp() {
        articleMapper = Mappers.getMapper(ArticleMapper.class);
        reviewableMapper = Mappers.getMapper(ReviewableMapper.class);
    }

    @Test
    void givenArticleDTO_whenToArticleUsingExpression_thenReturnsArticleWithNullStatus() {
        Article oldArticle1 = new Article("ID-1", "John Doe");
        Article oldArticle2 = new Article("ID-2", "John Doe");

        Article result1 = articleMapper.toArticleUsingExpression(new ArticleDTO("Updated article 1 title"), oldArticle1);
        Article result2 = articleMapper.toArticleUsingExpressionMethod(new ArticleDTO("Updated article 2 title"), oldArticle2);

        assertThat(result1.getReviewedBy()).isNull();
        assertThat(result2.getReviewedBy()).isNull();
        assertThat(result1.getTitle()).isEqualTo("Updated article 1 title");
        assertThat(result2.getTitle()).isEqualTo("Updated article 2 title");
    }

    @Test
    void givenArticleDTO_whenToArticleUsingIgnore_thenReturnsArticleWithNullStatus() {
        Article oldArticle1 = new Article("ID-1", "John Doe");

        Article result1 = articleMapper.toArticleUsingIgnore(new ArticleDTO("Updated article 1 title"), oldArticle1);

        assertThat(result1.getReviewedBy()).isNull();
        assertThat(result1.getTitle()).isEqualTo("Updated article 1 title");
    }

    @Test
    void givenArticleDTO_whenToArticleUsingAfterMapping_thenReturnsArticleWithNullStatus() {
        Article oldArticle1 = new Article("ID-1", "John Doe");

        Article result1 = articleMapper.toArticleUsingAfterMapping(new ArticleDTO("Updated article 1 title"), oldArticle1);

        assertThat(result1.getReviewedBy()).isNull();
        assertThat(result1.getTitle()).isEqualTo("Updated article 1 title");
    }

    @Test
    void givenArticleDTO_whenToArticleUsingQualifiedBy_thenReturnsArticleWithNullStatus() {
        Article result1 = articleMapper.toArticleUsingQualifiedBy(new ArticleDTO("Updated article 1 title"), new Article("ID-1", "John Doe"));

        assertThat(result1.getReviewedBy()).isNull();
        assertThat(result1.getTitle()).isEqualTo("Updated article 1 title");
    }

    @Test
    void givenArticleDTO_whenToReviewableUsingMapper_thenReturnsArticleWithNullStatus() {
        Reviewable result1 = reviewableMapper.toReviewable(new ArticleDTO("Updated article 1 title"));
        Reviewable result2 = reviewableMapper.toReviewable(new WeeklyNewsDTO());

        assertThat(result1).isInstanceOf(Article.class);
        assertThat(result2).isInstanceOf(WeeklyNews.class);
        assertThat(result1.getReviewedBy()).isNull();
        assertThat(result2.getReviewedBy()).isNull();
    }
}
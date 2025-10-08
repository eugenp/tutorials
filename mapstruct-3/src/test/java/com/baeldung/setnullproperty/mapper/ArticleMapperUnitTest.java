package com.baeldung.setnullproperty.mapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.baeldung.setnullproperty.dto.ArticleDTO;
import com.baeldung.setnullproperty.entity.Article;

class ArticleMapperUnitTest {

    private ArticleMapper articleMapper;

    @BeforeEach
    void setUp() {
        articleMapper = Mappers.getMapper(ArticleMapper.class);
    }

    //    @Test
    //    void givenArticleDTO_whenToArticleIgnoringStatus_thenReturnsArticleWithNullStatus() {
    //        Article result = articleMapper.toArticleUsingIgnore(new ArticleDTO("PENDING"));
    //
    //        assertThat(result.getReviewedBy()).isNull();
    //    }

    @Test
    void givenArticleDTO_whenToArticleUsingExpression_thenReturnsArticleWithNullStatus() {
        Article oldArticle1 = new Article("Title 1", "John Doe");
        Article oldArticle2 = new Article("Title 2", "John Doe");

        Article result1 = articleMapper.toArticleUsingExpression(new ArticleDTO("Updated article title"), oldArticle1);
        Article result2 = articleMapper.toArticleUsingExpressionMethod(new ArticleDTO("Updated article title"), oldArticle2);

        assertThat(result1.getReviewedBy()).isNull();
        assertThat(result2.getReviewedBy()).isNull();
        assertThat(result1.getTitle()).isEqualTo("Updated article title");
        assertThat(result2.getTitle()).isEqualTo("Updated article title");
    }

//    @Test
//    void givenArticleDTO_whenToArticleUsingAfterMapping_thenReturnsArticleWithNullStatus() {
//        Article result = articleMapper.toArticleUsingAfterMapping(new ArticleDTO("PENDING"));
//
//        assertThat(result.getReviewedBy()).isNull();
//    }

    //    @Test
    //    void givenArticleDTO_whenToArticleWithNullStatus_thenReturnsArticleWithNullStatus() {
    //        Article result = articleMapper.toArticleWithNullStatus(new ArticleDTO("PENDING"));
    //
    //        assertThat(result.getReviewedBy()).isNull();
    //    }
}
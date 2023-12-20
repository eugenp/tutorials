package com.baeldung.spring.data.jpa.spel.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.baeldung.spring.data.jpa.spel.NewsApplication;
import com.baeldung.spring.data.jpa.spel.entity.Article;
import com.baeldung.spring.data.jpa.spel.entity.ArticleWrapper;
import com.baeldung.spring.data.jpa.spel.extension.LocaleContextHolderExtension;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = NewsApplication.class,
    properties = {
        "spring.jpa.show-sql=true",
        "spring.jpa.generate-ddl=true",
    })
class ArticleRepositoryIntegrationTest {

    @Autowired
    private ArticleRepository articleRepository;

    private static final String ENGLISH = "eng";
    private static final Article SPORTS_ARTICLE
        = new Article(1L, "Sports Update",
        "The local team won their game last night...", ENGLISH);

    @Autowired
    private LocaleContextHolderExtension localeContextHolderExtension;

    @Test
    void whenContextStartRepositoryIsPresent() {
        assertNotNull(articleRepository, "Repository should be present");
    }

    @Test
    void whenContextStartContextHolderIsPresent() {
        assertNotNull(localeContextHolderExtension, "Context holder should be present");
        assertNotNull(localeContextHolderExtension.getRootObject());
    }

    @AfterEach
    void tearDown() {
        articleRepository.deleteAll();
    }

    @Test
    void givenArticleWhenCreateWithPositionalArgumentsPlaceholdersShouldBePersisted() {
        articleRepository.saveWithPositionalArguments(1L, SPORTS_ARTICLE.getTitle(),
            SPORTS_ARTICLE.getContent(),
            SPORTS_ARTICLE.getLanguage());
        final List<Article> articles = articleRepository.findAll();
        assertEquals(1, articles.size());
    }

    @Test
    void givenArticleWhenCreateWithPositionalSpELArgumentsPlaceholdersShouldBePersisted() {
        articleRepository.saveWithPositionalSpELArguments(1L, SPORTS_ARTICLE.getTitle(),
            SPORTS_ARTICLE.getContent(),
            SPORTS_ARTICLE.getLanguage());
        final List<Article> articles = articleRepository.findAll();
        assertEquals(1, articles.size());
    }

    @Test
    void givenArticleWhenCreateWithPositionalSpELArgumentsPlaceholdersWithEmptyCheckShouldStoreDefaultValue() {
        articleRepository.saveWithPositionalSpELArgumentsWithEmptyCheck(1L,
            SPORTS_ARTICLE.getTitle(), null, SPORTS_ARTICLE.getLanguage());
        final List<Article> articles = articleRepository.findAll();
        assertEquals(1, articles.size());
        assertEquals("Empty Article", articles.get(0).getContent());
    }

    @Test
    void givenArticleWhenCreateWithPositionalSpELArgumentsPlaceholdersWithEmptyCheckShouldStoreTheOriginalContent() {
        articleRepository.saveWithPositionalSpELArgumentsWithEmptyCheck(1L,
            SPORTS_ARTICLE.getTitle(), SPORTS_ARTICLE.getContent(), SPORTS_ARTICLE.getLanguage());
        final List<Article> articles = articleRepository.findAll();
        assertEquals(1, articles.size());
        assertEquals(SPORTS_ARTICLE, articles.get(0));
    }

    @Test
    void givenArticleWhenCreateWithNamedArgumentsPlaceholdersShouldBePersisted() {
        articleRepository.saveWithNamedArguments(1L, SPORTS_ARTICLE.getTitle(),
            SPORTS_ARTICLE.getContent(), SPORTS_ARTICLE.getLanguage());
        final List<Article> articles = articleRepository.findAll();
        assertEquals(1, articles.size());
    }

    @Test
    void givenArticleWhenCreateWithNamedSpELArgumentsPlaceholdersShouldBePersisted() {
        articleRepository.saveWithNamedSpELArguments(1L, SPORTS_ARTICLE.getTitle(),
            SPORTS_ARTICLE.getContent(), SPORTS_ARTICLE.getLanguage());
        final List<Article> articles = articleRepository.findAll();
        assertEquals(1, articles.size());
    }

    @Test
    void givenArticleWhenCreateWithNamedSpELArgumentsAndLowerCaseLanguagePlaceholdersConvertLanguageToLowerCase() {
        final String language = "ENG";
        articleRepository.saveWithNamedSpELArgumentsAndLowerCaseLanguage(1L,
            SPORTS_ARTICLE.getTitle(), SPORTS_ARTICLE.getContent(),
            language);
        final List<Article> articles = articleRepository.findAll();
        assertEquals(1, articles.size());
        assertEquals(language.toLowerCase(), articles.get(0).getLanguage());
    }

    @Test
    void givenArticleWhenCreateWithSingleObjectArgumentPlaceholdersShouldBePersisted() {
        articleRepository.saveWithSingleObjectSpELArgument(SPORTS_ARTICLE);
        final List<Article> articles = articleRepository.findAll();
        assertEquals(1, articles.size());
    }

    @Test
    void givenArticleWhenCreateWithSingleWrappedObjectArgumentPlaceholdersShouldBePersisted() {
        articleRepository.saveWithSingleWrappedObjectSpELArgument(new ArticleWrapper(SPORTS_ARTICLE));
        final List<Article> articles = articleRepository.findAll();
        assertEquals(1, articles.size());
    }

    @Test
    void givenInheritedQueryWhenSearchForArticlesWillReturnThem() {
        articleRepository.save(SPORTS_ARTICLE);
        final List<Article> articles = articleRepository.findAllEntitiesUsingEntityPlaceholder();
        assertEquals(1, articles.size());
    }

    @Test
    void givenInheritedNativeQueryWhenSearchForArticlesWillReturnThem() {
        articleRepository.save(SPORTS_ARTICLE);
        final List<Article> articles = articleRepository.findAllEntitiesUsingEntityPlaceholderWithNativeQuery();
        assertEquals(1, articles.size());
    }

}
package com.baeldung.jpa.enums;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArticleUnitTest {

    private static EntityManager em;
    private static EntityManagerFactory emFactory;

    @BeforeClass
    public static void setup() {
        Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        emFactory = Persistence.createEntityManagerFactory("jpa-h2", properties);
        em = emFactory.createEntityManager();
    }

    @Test
    public void shouldPersistStatusEnumOrdinalValue() {
        // given
        Article article = new Article();
        article.setId(1);
        article.setTitle("ordinal title");
        article.setStatus(Status.OPEN);

        // when
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(article);
        tx.commit();

        // then
        Article persistedArticle = em.find(Article.class, 1);

        assertEquals(1, persistedArticle.getId());
        assertEquals("ordinal title", persistedArticle.getTitle());
        assertEquals(Status.OPEN, persistedArticle.getStatus());
    }

    @Test
    public void shouldPersistTypeEnumStringValue() {
        // given
        Article article = new Article();
        article.setId(2);
        article.setTitle("string title");
        article.setType(Type.EXTERNAL);

        // when
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(article);
        tx.commit();

        // then
        Article persistedArticle = em.find(Article.class, 2);

        assertEquals(2, persistedArticle.getId());
        assertEquals("string title", persistedArticle.getTitle());
        assertEquals(Type.EXTERNAL, persistedArticle.getType());
    }

    @Test
    public void shouldPersistPriorityIntValue() {
        // given
        Article article = new Article();
        article.setId(3);
        article.setTitle("callback title");
        article.setPriority(Priority.HIGH);

        // when
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(article);
        tx.commit();

        // then
        Article persistedArticle = em.find(Article.class, 3);

        assertEquals(3, persistedArticle.getId());
        assertEquals("callback title", persistedArticle.getTitle());
        assertEquals(Priority.HIGH, persistedArticle.getPriority());

    }

    @Test
    public void shouldPersistCategoryEnumConvertedValue() {
        // given
        Article article = new Article();
        article.setId(4);
        article.setTitle("converted title");
        article.setCategory(Category.MUSIC);

        // when
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(article);
        tx.commit();

        // then
        Article persistedArticle = em.find(Article.class, 4);

        assertEquals(4, persistedArticle.getId());
        assertEquals("converted title", persistedArticle.getTitle());
        assertEquals(Category.MUSIC, persistedArticle.getCategory());
    }

    @Test
    public void shouldFindArticleByCategory() {
        // given
        Article article = new Article();
        article.setId(5);
        article.setTitle("static");
        article.setCategory(Category.SPORT);

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(article);
        tx.commit();

        String jpql = "select a from Article a where a.category = com.baeldung.jpa.enums.Category.SPORT";

        // when
        List<Article> articles = em.createQuery(jpql, Article.class).getResultList();

        // then
        assertEquals(1, articles.size());
        assertEquals(Category.SPORT, articles.get(0).getCategory());
        assertEquals("static", articles.get(0).getTitle());
    }

    @Test
    public void shouldFindArticleByCategoryParameter() {
        // given
        Article article = new Article();
        article.setId(6);
        article.setTitle("dynamic");
        article.setCategory(Category.TECHNOLOGY);

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(article);
        tx.commit();

        String jpql = "select a from Article a where a.category = :category";

        // when
        TypedQuery<Article> query = em.createQuery(jpql, Article.class);
        query.setParameter("category", Category.TECHNOLOGY);
        List<Article> articles = query.getResultList();

        // then
        assertEquals(1, articles.size());
        assertEquals(Category.TECHNOLOGY, articles.get(0).getCategory());
        assertEquals("dynamic", articles.get(0).getTitle());
    }
}
package com.baeldung.cockroachdb;

import com.baeldung.cockroachdb.domain.Article;
import com.baeldung.cockroachdb.repository.ArticleRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArticleRepositoryLiveTest {

    private static final String TABLE_NAME = "articles";

    private Connection con;
    private ArticleRepository articleRepository;

    @Before
    public void connect() throws Exception {
        Class.forName("org.postgresql.Driver");
        con = DriverManager.getConnection("jdbc:postgresql://localhost:26257/testdb", "user17", "qwerty");

        articleRepository = new ArticleRepository(con);
    }

    @Test
    public void whenCreatingTable_thenCreatedCorrectly() throws Exception {
        articleRepository.deleteTable();
        articleRepository.createTable();

        PreparedStatement preparedStatement = con.prepareStatement("SHOW TABLES");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<String> tables = new ArrayList<>();
        while (resultSet.next()) {
            tables.add(resultSet.getString("Table"));
        }

        assertTrue(tables.stream().anyMatch(t -> t.equals(TABLE_NAME)));
    }

    @Test
    public void whenAlteringTheTable_thenColumnAddedCorrectly() throws SQLException {
        articleRepository.deleteTable();
        articleRepository.createTable();

        String columnName = "creationdate";
        articleRepository.alterTable(columnName, "DATE");

        String query = "SHOW COLUMNS FROM " + TABLE_NAME;
        PreparedStatement preparedStatement = con.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<String> columns = new ArrayList<>();
        while (resultSet.next()) {
            columns.add(resultSet.getString("Field"));
        }

        assertTrue(columns.stream().anyMatch(c -> c.equals(columnName)));
    }

    @Test
    public void whenInsertingNewArticle_thenArticleExists() throws SQLException {
        articleRepository.deleteTable();
        articleRepository.createTable();

        String title = "Guide to CockroachDB in Java";
        String author = "baeldung";
        Article article = new Article(UUID.randomUUID(), title, author);
        articleRepository.insertArticle(article);

        Article savedArticle = articleRepository.selectByTitle(title);
        assertEquals(article.getTitle(), savedArticle.getTitle());
    }

    @Test
    public void whenSelectingAllArticles_thenAllArticlesAreReturned() throws SQLException {
        articleRepository.deleteTable();
        articleRepository.createTable();

        Article article = new Article(UUID.randomUUID(), "Guide to CockroachDB in Java", "baeldung");
        articleRepository.insertArticle(article);

        article = new Article(UUID.randomUUID(), "A Guide to MongoDB with Java", "baeldung");
        articleRepository.insertArticle(article);

        List<Article> savedArticles = articleRepository.selectAll();

        assertEquals(2, savedArticles.size());
        assertTrue(savedArticles.stream().anyMatch(a -> a.getTitle().equals("Guide to CockroachDB in Java")));
        assertTrue(savedArticles.stream().anyMatch(a -> a.getTitle().equals("A Guide to MongoDB with Java")));
    }

    @Test
    public void whenDeletingArticleByTtile_thenArticleIsDeleted() throws SQLException {
        articleRepository.deleteTable();
        articleRepository.createTable();

        Article article = new Article(UUID.randomUUID(), "Guide to CockroachDB in Java", "baeldung");
        articleRepository.insertArticle(article);

        article = new Article(UUID.randomUUID(), "A Guide to MongoDB with Java", "baeldung");
        articleRepository.insertArticle(article);

        articleRepository.deleteArticleByTitle("A Guide to MongoDB with Java");

        List<Article> savedArticles = articleRepository.selectAll();
        assertEquals(1, savedArticles.size());
        assertTrue(savedArticles.stream().anyMatch(a -> a.getTitle().equals("Guide to CockroachDB in Java")));
        assertFalse(savedArticles.stream().anyMatch(a -> a.getTitle().equals("A Guide to MongoDB with Java")));
    }

    @Test(expected = PSQLException.class)
    public void whenDeletingATable_thenExceptionIfAccessed() throws SQLException {
        articleRepository.createTable();
        articleRepository.deleteTable();

        StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME);

        final String query = sb.toString();
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.executeQuery();
    }

    @Test
    public void whenTruncatingATable_thenEmptyTable() throws SQLException {
        articleRepository.deleteTable();
        articleRepository.createTable();

        Article article = new Article(UUID.randomUUID(), "Guide to CockroachDB in Java", "baeldung");
        articleRepository.insertArticle(article);

        article = new Article(UUID.randomUUID(), "A Guide to MongoDB with Java", "baeldung");
        articleRepository.insertArticle(article);

        articleRepository.truncateTable();

        List<Article> savedArticles = articleRepository.selectAll();
        assertEquals(0, savedArticles.size());
    }

    @Test
    public void whenInsertingTwoArticlesWithSamePrimaryKeyInASingleTransaction_thenRollback() throws SQLException {
        articleRepository.deleteTable();
        articleRepository.createTable();

        try {
            con.setAutoCommit(false);

            UUID articleId = UUID.randomUUID();

            Article article = new Article(articleId, "Guide to CockroachDB in Java", "baeldung");
            articleRepository.insertArticle(article);

            article = new Article(articleId, "A Guide to MongoDB with Java", "baeldung");
            articleRepository.insertArticle(article);

            con.commit();
        } catch (Exception e) {
            con.rollback();
        } finally {
            con.setAutoCommit(true);
        }

        List<Article> savedArticles = articleRepository.selectAll();
        assertEquals(0, savedArticles.size());
    }

    @Test
    public void whenInsertingTwoArticlesInASingleTransaction_thenInserted() throws SQLException {
        articleRepository.deleteTable();
        articleRepository.createTable();

        try {
            con.setAutoCommit(false);

            Article article = new Article(UUID.randomUUID(), "Guide to CockroachDB in Java", "baeldung");
            articleRepository.insertArticle(article);

            article = new Article(UUID.randomUUID(), "A Guide to MongoDB with Java", "baeldung");
            articleRepository.insertArticle(article);

            con.commit();
        } catch (Exception e) {
            con.rollback();
        } finally {
            con.setAutoCommit(true);
        }

        List<Article> savedArticles = articleRepository.selectAll();
        assertEquals(2, savedArticles.size());
        assertTrue(savedArticles.stream().anyMatch(a -> a.getTitle().equals("Guide to CockroachDB in Java")));
        assertTrue(savedArticles.stream().anyMatch(a -> a.getTitle().equals("A Guide to MongoDB with Java")));
    }

    @After
    public void disconnect() throws SQLException {
        articleRepository = null;
        con.close();
        con = null;
    }
}

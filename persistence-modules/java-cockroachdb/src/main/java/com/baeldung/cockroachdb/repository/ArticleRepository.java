package com.baeldung.cockroachdb.repository;

import com.baeldung.cockroachdb.domain.Article;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Repository for the articles table related operations
 */
public class ArticleRepository {

    private static final String TABLE_NAME = "articles";
    private Connection connection;

    public ArticleRepository(Connection connection) {
        this.connection = connection;
    }

    /**
     * Creates the articles table.
     */
    public void createTable() throws SQLException {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME)
          .append("(id uuid PRIMARY KEY, ")
          .append("title string,")
          .append("author string)");

        final String query = sb.toString();
        Statement stmt = connection.createStatement();
        stmt.execute(query);
    }

    /**
     * Alter the articles table adding a column
     *
     * @param columnName Column name of the additional column
     * @param columnType Column type of the additional column
     * @throws SQLException
     */
    public void alterTable(String columnName, String columnType) throws SQLException {
        StringBuilder sb = new StringBuilder("ALTER TABLE ").append(TABLE_NAME)
          .append(" ADD ")
          .append(columnName)
          .append(" ")
          .append(columnType);

        final String query = sb.toString();
        Statement stmt = connection.createStatement();
        stmt.execute(query);
    }

    /**
     * Insert a new article in the articles table
     *
     * @param article New article to insert
     * @throws SQLException
     */
    public void insertArticle(Article article) throws SQLException {
        StringBuilder sb = new StringBuilder("INSERT INTO ").append(TABLE_NAME)
          .append("(id, title, author) ")
          .append("VALUES (?,?,?)");

        final String query = sb.toString();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, article.getId().toString());
        preparedStatement.setString(2, article.getTitle());
        preparedStatement.setString(3, article.getAuthor());
        preparedStatement.execute();
    }

    /**
     * Select article by Title
     *
     * @param title title of the article to retrieve
     * @return article with the given title
     * @throws SQLException
     */
    public Article selectByTitle(String title) throws SQLException {
        StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME)
          .append(" WHERE title = ?");

        final String query = sb.toString();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, title);

        try (ResultSet rs = preparedStatement.executeQuery()) {

            List<Article> articles = new ArrayList<>();

            while (rs.next()) {
                Article article = new Article(
                    UUID.fromString(rs.getString("id")), 
                    rs.getString("title"), 
                    rs.getString("author")
                    );
                articles.add(article);
            }
            return articles.get(0);
        }

    }

    /**
     * Select all the articles
     *
     * @return list of all articles
     * @throws SQLException
     */
    public List<Article> selectAll() throws SQLException {
        StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME);

        final String query = sb.toString();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        try (ResultSet rs = preparedStatement.executeQuery()) {

            List<Article> articles = new ArrayList<>();

            while (rs.next()) {
                Article article = new Article(
                  UUID.fromString(rs.getString("id")), 
                  rs.getString("title"), 
                  rs.getString("author")
                  );
                articles.add(article);
            }
            return articles;
        }
    }

    /**
     * Delete article by title
     *
     * @param title title of the article to delete
     * @throws SQLException
     */
    public void deleteArticleByTitle(String title) throws SQLException {
        StringBuilder sb = new StringBuilder("DELETE FROM ").append(TABLE_NAME)
          .append(" WHERE title = ?");

        final String query = sb.toString();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, title);
        preparedStatement.execute();
    }

    /**
     * Delete all rows in the table
     *
     * @throws SQLException
     */
    public void truncateTable() throws SQLException {
        StringBuilder sb = new StringBuilder("TRUNCATE TABLE ").append(TABLE_NAME);

        final String query = sb.toString();
        Statement stmt = connection.createStatement();
        stmt.execute(query);
    }

    /**
     * Delete table
     */
    public void deleteTable() throws SQLException {
        StringBuilder sb = new StringBuilder("DROP TABLE IF EXISTS ").append(TABLE_NAME);

        final String query = sb.toString();
        Statement stmt = connection.createStatement();
        stmt.execute(query);
    }
}

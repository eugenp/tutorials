package com.baeldung.spring.jdbc.joins;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ArticleWithAuthorDAOLiveTest {
        private Connection connection;

        private ArticleWithAuthorDAO articleWithAuthorDAO;

        @Before
        public void setup() throws ClassNotFoundException, SQLException {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/myDb", "user", "pass");
                articleWithAuthorDAO = new ArticleWithAuthorDAO(connection);
                Statement statement = connection.createStatement();
                String createAuthorSql = "CREATE TABLE IF NOT EXISTS AUTHOR (ID int NOT NULL PRIMARY KEY, FIRST_NAME varchar(255), LAST_NAME varchar(255));";
                String createArticleSql = "CREATE TABLE IF NOT EXISTS ARTICLE (ID int NOT NULL PRIMARY KEY, TITLE varchar(255) NOT NULL, AUTHOR_ID int, FOREIGN KEY(AUTHOR_ID) REFERENCES AUTHOR(ID));";
                statement.execute(createAuthorSql);
                statement.execute(createArticleSql);

                insertTestData(statement);
        }

        @Test
        public void whenQueryWithInnerJoin_thenShouldReturnProperRows() {
                List<ArticleWithAuthor> articleWithAuthorList = articleWithAuthorDAO.articleInnerJoinAuthor();

                assertThat(articleWithAuthorList).hasSize(4);
                assertThat(articleWithAuthorList).noneMatch(row -> row.getAuthorFirstName() == null || row.getTitle() == null);
        }

        @Test
        public void whenQueryWithLeftJoin_thenShouldReturnProperRows() {
                List<ArticleWithAuthor> articleWithAuthorList = articleWithAuthorDAO.articleLeftJoinAuthor();

                assertThat(articleWithAuthorList).hasSize(5);
                assertThat(articleWithAuthorList).anyMatch(row -> row.getAuthorFirstName() == null);
        }

        @Test
        public void whenQueryWithRightJoin_thenShouldReturnProperRows() {
                List<ArticleWithAuthor> articleWithAuthorList = articleWithAuthorDAO.articleRightJoinAuthor();

                assertThat(articleWithAuthorList).hasSize(5);
                assertThat(articleWithAuthorList).anyMatch(row -> row.getTitle() == null);
        }

        @Test
        public void whenQueryWithFullJoin_thenShouldReturnProperRows() {
                List<ArticleWithAuthor> articleWithAuthorList = articleWithAuthorDAO.articleFullJoinAuthor();

                assertThat(articleWithAuthorList).hasSize(6);
                assertThat(articleWithAuthorList).anyMatch(row -> row.getTitle() == null);
                assertThat(articleWithAuthorList).anyMatch(row -> row.getAuthorFirstName() == null);
        }

        @After
        public void cleanup() throws SQLException {
                connection.createStatement().execute("DROP TABLE ARTICLE");
                connection.createStatement().execute("DROP TABLE AUTHOR");
                connection.close();
        }

        public void insertTestData(Statement statement) throws SQLException {
                String insertAuthors = "INSERT INTO AUTHOR VALUES "
                    + "(1, 'Siena', 'Kerr'),"
                    + "(2, 'Daniele', 'Ferguson'),"
                    + "(3, 'Luciano', 'Wise'),"
                    + "(4, 'Jonas', 'Lugo');";
                String insertArticles = "INSERT INTO ARTICLE VALUES "
                    + "(1, 'First steps in Java', 1),"
                    + "(2, 'SpringBoot tutorial', 1),"
                    + "(3, 'Java 12 insights', null),"
                    + "(4, 'SQL JOINS', 2),"
                    + "(5, 'Introduction to Spring Security', 3);";
                statement.execute(insertAuthors);
                statement.execute(insertArticles);
        }
}

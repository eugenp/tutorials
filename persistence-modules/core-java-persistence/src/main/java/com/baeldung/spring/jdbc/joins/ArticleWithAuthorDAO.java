package com.baeldung.spring.jdbc.joins;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

class ArticleWithAuthorDAO {

        private static final String QUERY_TEMPLATE = "SELECT ARTICLE.TITLE, AUTHOR.LAST_NAME, AUTHOR.FIRST_NAME FROM ARTICLE %s AUTHOR ON AUTHOR.id=ARTICLE.AUTHOR_ID";
        private final Connection connection;

        ArticleWithAuthorDAO(Connection connection) {
                this.connection = connection;
        }

        List<ArticleWithAuthor> articleInnerJoinAuthor() {
                String query = String.format(QUERY_TEMPLATE, "INNER JOIN");
                return executeQuery(query);
        }

        List<ArticleWithAuthor> articleLeftJoinAuthor() {
                String query = String.format(QUERY_TEMPLATE, "LEFT JOIN");
                return executeQuery(query);
        }

        List<ArticleWithAuthor> articleRightJoinAuthor() {
                String query = String.format(QUERY_TEMPLATE, "RIGHT JOIN");
                return executeQuery(query);
        }

        List<ArticleWithAuthor> articleFullJoinAuthor() {
                String query = String.format(QUERY_TEMPLATE, "FULL JOIN");
                return executeQuery(query);
        }

        private List<ArticleWithAuthor> executeQuery(String query) {
                try (Statement statement = connection.createStatement()) {
                        ResultSet resultSet = statement.executeQuery(query);
                        return mapToList(resultSet);
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                return null;
        }

        private List<ArticleWithAuthor> mapToList(ResultSet resultSet) throws SQLException {
                List<ArticleWithAuthor> list = new ArrayList<>();
                while (resultSet.next()) {
                        ArticleWithAuthor articleWithAuthor = new ArticleWithAuthor(
                            resultSet.getString("TITLE"),
                            resultSet.getString("FIRST_NAME"),
                            resultSet.getString("LAST_NAME")
                        );
                        list.add(articleWithAuthor);
                }
                return list;
        }
}

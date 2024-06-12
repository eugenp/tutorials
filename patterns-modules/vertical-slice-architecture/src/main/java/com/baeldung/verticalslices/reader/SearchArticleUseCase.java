package com.baeldung.verticalslices.reader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
class SearchArticleUseCase {

    private final JdbcClient jdbcClient;

    record Article(String name, String slug, String description, String category, long authorId) {
    }

    public Optional<Article> search(String slug) {

        String sql = """
            SELECT id, name, slug, description, category, authorid
            FROM articles
            WHERE slug = ?;
            """;

        return jdbcClient.sql(sql)
            .param(slug)
            .query(this::mapRowToArticle)
            .optional();
    }

    private Article mapRowToArticle(ResultSet rs, int rowNum) throws SQLException {
        return new Article(rs.getString("name"), rs.getString("slug"), rs.getString("description"), rs.getString("category"), rs.getLong("authorid"));
    }

    SearchArticleUseCase(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }
}

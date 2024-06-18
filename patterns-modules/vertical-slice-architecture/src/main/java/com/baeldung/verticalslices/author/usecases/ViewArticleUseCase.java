package com.baeldung.verticalslices.author.usecases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
class ViewArticleUseCase {

    private static final String FIND_BY_SLUG_SQL = """
        SELECT id, name, slug, content, authorid
        FROM articles
        WHERE slug = ?;
        """;

    private final JdbcClient jdbcClient;

    public Optional<ViewArticleProjection> view(String slug) {
        return jdbcClient.sql(FIND_BY_SLUG_SQL)
            .param(slug)
            .query(this::mapArticleProjection)
            .optional();
    }

    record ViewArticleProjection(String name, String slug, String content, Long authorId) {
    }

    private ViewArticleProjection mapArticleProjection(ResultSet rs, int rowNum) throws SQLException {
        return new ViewArticleProjection(rs.getString("name"), rs.getString("slug"), rs.getString("content"), rs.getLong("authorid"));
    }

    ViewArticleUseCase(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }
}

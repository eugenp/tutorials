package com.baeldung.verticalslices.reader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
class SearchArticleUseCase {

    private static final String FIND_BY_SLUG_SQL = """
        SELECT id, name, slug, description, category, authorid
        FROM articles
        WHERE slug = ?;
        """;

    private final JdbcClient jdbcClient;

    public Optional<SearchArticleDto> search(String slug) {
        return jdbcClient.sql(FIND_BY_SLUG_SQL)
            .param(slug)
            .query(this::mapArticleProjection)
            .optional();
    }

    record SearchArticleDto(String name, String slug, String description, String category, Long authorId) {
    }

    private SearchArticleDto mapArticleProjection(ResultSet rs, int rowNum) throws SQLException {
        return new SearchArticleDto(rs.getString("name"), rs.getString("slug"), rs.getString("description"), rs.getString("category"), rs.getLong("authorid"));
    }

    SearchArticleUseCase(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }
}

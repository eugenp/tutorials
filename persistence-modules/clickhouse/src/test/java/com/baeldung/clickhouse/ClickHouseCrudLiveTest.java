package com.baeldung.clickhouse;

import org.springframework.jdbc.core.JdbcTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.clickhouse.ClickHouseContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class ClickHouseCrudLiveTest {

    @Container
    static ClickHouseContainer clickHouse = new ClickHouseContainer("clickhouse/clickhouse-server:24.11");;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", clickHouse::getJdbcUrl);
        registry.add("spring.datasource.username", clickHouse::getUsername);
        registry.add("spring.datasource.password", clickHouse::getPassword);
        registry.add("spring.datasource.driver-class-name", clickHouse::getDriverClassName);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void whenAuthorSaved_thenDatabaseContainsRecord() {
        Author author = Author.create("John Doe", "doe.john@baeldung.com");

        jdbcTemplate.update(
            """
                INSERT INTO authors (id, name, email, created_at)
                VALUES (?, ?, ?, ?);
            """,
            author.id(),
            author.name(),
            author.email(),
            author.createdAt()
        );

        List<Author> retrievedAuthors = jdbcTemplate.query(
            "SELECT * FROM authors WHERE id = ?",
            (ResultSet resultSet, int rowNum) -> new Author(
                UUID.fromString(resultSet.getString("id")),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getObject("created_at", LocalDateTime.class)
            ),
            author.id()
        );

        assertThat(retrievedAuthors)
            .hasSize(1)
            .first()
            .satisfies(retrievedAuthor -> {
                assertThat(retrievedAuthor.id()).isEqualTo(author.id());
                assertThat(retrievedAuthor.name()).isEqualTo(author.name());
                assertThat(retrievedAuthor.email()).isEqualTo(author.email());
                assertThat(retrievedAuthor.createdAt()).isNotNull();
            });
    }

}

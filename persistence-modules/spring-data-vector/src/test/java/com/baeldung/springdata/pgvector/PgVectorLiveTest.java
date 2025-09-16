package com.baeldung.springdata.pgvector;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Score;
import org.springframework.data.domain.ScoringFunction;
import org.springframework.data.domain.Vector;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
@Import(PgVectorTestConfiguration.class)
@ActiveProfiles("pgvector")
@Sql(scripts = "/pgvector-data-setup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PgVectorLiveTest {
    private static final Logger logger = LoggerFactory.getLogger(PgVectorLiveTest.class);

    @Autowired
    BookRepository bookRepository;

    @Autowired
    private PostgreSQLContainer pgVectorSQLContainer;

    @AfterAll
    void clean() {
        pgVectorSQLContainer.stop();
    }

    @Test
    void whenSearchByYearPublishedAndEmbeddingNear_thenResult() {
        //String query = "Which document has the details about Django?";
        Vector embedding = Vector.of(
            -0.34916985034942627f, 0.5338794589042664f,
            0.43527376651763916f, -0.6110032200813293f, -0.17396864295005798f
        );

        var results = bookRepository.searchByYearPublishedAndEmbeddingNear(
            "2022", embedding,
            Score.of(0.9, ScoringFunction.euclidean())
        );
        assertThat(results).isNotNull();
        assertThat(results.getContent().size()).isGreaterThan(0);

        results.getContent()
            .forEach(book -> assertThat(book.getContent().getYearPublished()).isEqualTo("2022"));
    }

    @Test
    void testSearchTop3ByYearPublished() {
        List<Book> books = bookRepository.searchTop3ByYearPublished("2022");
        assertThat(books).isNotEmpty().hasSizeGreaterThan(1);
        books.forEach(
            book -> assertThat(book.getYearPublished()).isEqualTo("2022")
        );
    }

}

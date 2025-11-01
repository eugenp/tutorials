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
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Range;
import org.springframework.data.domain.Score;
import org.springframework.data.domain.ScoringFunction;
import org.springframework.data.domain.SearchResult;
import org.springframework.data.domain.SearchResults;
import org.springframework.data.domain.Similarity;
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
    PGvectorBookRepository pgvectorBookRepository;

    @Autowired
    private PostgreSQLContainer pgVectorSQLContainer;

    @AfterAll
    void clean() {
        pgVectorSQLContainer.stop();
    }

    @Test
    void whenSearchByYearPublishedAndEmbeddingNear_thenResult() {
        Vector embedding = getEmbedding("Which document has the details about Django?");

        SearchResults<Book> results = pgvectorBookRepository.searchByYearPublishedAndEmbeddingNear(
            "2022", embedding,
            Score.of(0.9, ScoringFunction.euclidean())
        );
        assertThat(results).isNotNull();

        List<SearchResult<Book>> resultList = results.getContent();

        assertThat(resultList.size()).isGreaterThan(0);

        resultList.forEach(book -> assertThat(book.getContent().getYearPublished()).isEqualTo("2022"));
    }

    @Test
    void whenSearchByYearPublishedAndEmbeddingWithin_thenResult() {
        Vector embedding = getEmbedding("Which document has the details about Django?");

        Range<Similarity> range = Range.closed(
            Similarity.of(0.7, ScoringFunction.cosine()),
            Similarity.of(0.9, ScoringFunction.cosine())
        );
        SearchResults<Book> results = pgvectorBookRepository.searchByYearPublishedAndEmbeddingWithin(
            "2022", embedding, range, Limit.of(5)
        );
        assertThat(results).isNotNull();

        List<SearchResult<Book>> resultList = results.getContent();

        assertThat(resultList.size()).isGreaterThan(0).isLessThanOrEqualTo(5);

        resultList.forEach(book -> {
            assertThat(book.getContent().getYearPublished()).isEqualTo("2022");
            assertThat(book.getScore().getValue()).isBetween(0.7, 0.9);
        });
    }

    private Vector getEmbedding(String query) {
        return  Vector.of(
            -0.34916985034942627f, 0.5338794589042664f,
            0.43527376651763916f, -0.6110032200813293f, -0.17396864295005798f
        );
    }
}

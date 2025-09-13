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
import org.springframework.data.domain.Range;
import org.springframework.data.domain.Score;
import org.springframework.data.domain.ScoringFunction;
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
public class SpringDataVectorUnitTest {
    private static final Logger logger = LoggerFactory.getLogger(SpringDataVectorUnitTest.class);

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    private PostgreSQLContainer pgVectorSQLContainer;

    @AfterAll
    void clean() {
        pgVectorSQLContainer.stop();
    }

    @Test
    void testFindByYearPublishedAndEmbeddingNear() {
        //String query = "Which document has the details about Django?";
        Vector embedding = Vector.of( -0.34916985034942627f, 0.5338794589042664f, 0.43527376651763916f,
            -0.6110032200813293f, -0.17396864295005798f);

        var results = documentRepository.searchTop3ByYearPublishedAndTheEmbeddingNear("2022", embedding,
            Score.of(0.7, ScoringFunction.cosine()));
        results.getContent().forEach(content -> logger.info("Content: {}, Score: {} = {}", content.getContent().getContent(),
            content.getScore().getFunction().getName(), content.getScore().getValue()));
    }

    @Test
    void testSearchTop3ByYearPublished() {
        List<Document> documents = documentRepository.searchTop3ByYearPublished("2022");
        assertThat(documents).isNotEmpty();
        documents.forEach(document -> logger.info("Document: {}, Content: {}, published: {}",
                document.getId(), document.getContent(), document.getYearPublished()));
    }
/*
    @Test
    void testSearchByYearPublishedAndTheEmbeddingWithin() {
        String query = "Which document has the details about Django?";
        Vector embedding = Vector.of( -0.34916985034942627, 0.5338794589042664, 0.43527376651763916,
            -0.6110032200813293, -0.17396864295005798);

        Range<Similarity> range = Range.closed(Similarity.of(0.7), Similarity.of(1.0));

        var results = documentRepository.searchByYearPublishedAndTheEmbeddingWithin("2022", embedding, range,
            Limit.of(3));
        results.getContent().forEach(content -> {
            logger.info("Content: {}, Score: {} = {}", content.getContent().getContent(),
                content.getScore().getFunction().getName(), content.getScore().getValue());
        });
    }*/
/*
    private Vector createEmbedding(String query) {
        return Vector.of( -0.34916985034942627, 0.5338794589042664, 0.43527376651763916,
            -0.6110032200813293, -0.17396864295005798);
    }*/
}

package com.baeldung.springdata.mongodb;

import com.baedlung.springdata.mongodb.DatasetupService;
import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.mongodb.MongoDBAtlasLocalContainer;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = { SpringDataMongoDBVectorApplication.class })
@Import(MongoDBTestConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("mongodb")
public class MongoDBVectorLiveTest {
    Logger logger = LoggerFactory.getLogger(MongoDBVectorLiveTest.class);

    @Autowired
    DatasetupService datasetupService;

    @Autowired
    MongoDbBookRepository mongoDbBookRepository;

    @Autowired
    MongoDBAtlasLocalContainer mongoDBAtlasLocalContainer;

    @BeforeAll
    void setup() throws IOException, CsvValidationException {
        datasetupService.setup();
    }

    @AfterAll
    void clean() {
        mongoDBAtlasLocalContainer.stop();
    }

    private Vector getEmbedding(String query) {
        return  Vector.of(
            -0.34916985034942627f, 0.5338794589042664f,
            0.43527376651763916f, -0.6110032200813293f, -0.17396864295005798f
        );
    }

    @Test
    void whenSearchByYearPublishedAndEmbeddingNear_thenReturnResult() {
        Vector embedding = getEmbedding("Which document has the details about Django?");

        SearchResults<Book> results = mongoDbBookRepository.searchByYearPublishedAndEmbeddingNear("2022",
            embedding, Score.of(0.9));
        List<SearchResult<Book>> resultLst = results.getContent();

        assertThat(resultLst.size()).isGreaterThan(0);

        resultLst.forEach(content -> {
            Book book = content.getContent();
            assertThat(book.getYearPublished()).isEqualTo("2022");
        });
    }

    @Test
    void whenSearchByYearPublishedAndEmbeddingWithin_thenReturnResult() {
        Vector embedding = getEmbedding("Which document has the details about Django?");

        Range<Similarity> range = Range.closed(Similarity.of(0.7), Similarity.of(0.9));
        SearchResults<Book> results = mongoDbBookRepository.searchByYearPublishedAndEmbeddingWithin("2022",
            embedding, range);

        assertThat(results).isNotNull();

        List<SearchResult<Book>> resultList = results.getContent();

        assertThat(resultList.size()).isGreaterThan(0).isLessThanOrEqualTo(10);

        resultList.forEach(book -> {
            assertThat(book.getContent().getYearPublished()).isEqualTo("2022");
            assertThat(book.getScore().getValue()).isBetween(0.7, 0.9);
        });
    }
}
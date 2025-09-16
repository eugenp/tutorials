package com.baedlung.springdata.mongodb;

import static org.springframework.data.mongodb.core.index.VectorIndex.SimilarityFunction.COSINE;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.SecureRandom;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Range;
import org.springframework.data.domain.Score;
import org.springframework.data.domain.SearchResults;
import org.springframework.data.domain.Similarity;
import org.springframework.data.domain.Vector;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.SearchIndexStatus;
import org.springframework.data.mongodb.core.index.VectorIndex;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.mongodb.MongoDBAtlasLocalContainer;

import com.baeldung.springdata.mongodb.Book;
import com.baeldung.springdata.mongodb.BookRepository;
import com.baeldung.springdata.mongodb.SpringDataMongoDBVectorApplication;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

@SpringBootTest(classes = { SpringDataMongoDBVectorApplication.class })
@Import(MongoDBTestConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("mongodb")
public class MongoDBVectorLiveTest {
    Logger logger = LoggerFactory.getLogger(MongoDBVectorLiveTest.class);

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    BookRepository bookRepository;

    @Autowired
    MongoDBAtlasLocalContainer mongoDBAtlasLocalContainer;

    @BeforeAll
    void setup() throws IOException, CsvValidationException {
        if (mongoTemplate.collectionExists(Book.class)) {
            mongoTemplate.dropCollection(Book.class);
        }

        mongoTemplate.createCollection(Book.class);
        VectorIndex vectorIndex = new VectorIndex("book-vector-index")
            .addVector("embedding", vector -> vector.dimensions(5).similarity(COSINE))
            .addFilter("yearPublished");

        mongoTemplate.searchIndexOps(Book.class)
            .createIndex(vectorIndex);

        try (InputStream is = getClass().getClassLoader()
            .getResourceAsStream("mongodb-data-setup.csv");
             CSVReader reader = new CSVReader(new InputStreamReader(is))) {
            String[] line;
            reader.readNext(); // skip header row
            while ((line = reader.readNext()) != null) {
                String content = line[0];
                String yearPublished = line[1];
                String embeddingStr = line[2].replaceAll("\\[|\\]", "");
                String[] embeddingValues = embeddingStr.split(",");

                float[] embedding = new float[embeddingValues.length];
                for (int i = 0; i < embeddingValues.length; i++) {
                    embedding[i] = Float.parseFloat(embeddingValues[i].trim());
                }
                Vector theVectorEmbedding = Vector.of(embedding);

                Book doc = new Book(generateRandomString(), content, yearPublished, theVectorEmbedding);

                bookRepository.save(doc);
            }
        }

        try {
            waitForIndexReady(mongoTemplate, Book.class, "book-vector-index");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    void waitForIndexReady(MongoTemplate mongoTemplate, Class<?> entityClass, String indexName) throws InterruptedException {
        int MAX_ATTEMPTS = 30;
        int SLEEP_MS = 2000;
        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            SearchIndexStatus status = mongoTemplate.searchIndexOps(entityClass).status(indexName);
            logger.info("Vector index status: {}", status);
            if (status == SearchIndexStatus.READY) {
                return;
            }
            Thread.sleep(SLEEP_MS); // Wait 2 seconds before checking again
        }
        throw new RuntimeException("Vector index did not become READY after waiting.");
    }

    private static String generateRandomString() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(5);
        for (int i = 0; i < 5; i++) {
            int idx = random.nextInt(chars.length());
            sb.append(chars.charAt(idx));
        }
        return sb.toString();
    }

    @AfterAll
    void clean() {
        mongoDBAtlasLocalContainer.stop();
    }

    @Test
    void whenSearchByEmbeddingNear_thenReturnResult() {
        //    String query = "Which document has the details about Django?";
        Vector embedding = Vector.of(-0.34916985034942627f, 0.5338794589042664f, 0.43527376651763916f,
            -0.6110032200813293f, -0.17396864295005798f);
        SearchResults<Book> results = bookRepository.searchByEmbeddingNear(embedding, Similarity.of(.9));
        logger.info("Results found: {}", results.stream()
            .count());
        results.getContent()
            .forEach(book -> logger.info("Book: {}, yearPublished: {}", book.getContent()
                .getName(), book.getContent()
                .getYearPublished()));
    }

    @Test
    void whenSearchTop3ByEmbeddingNear_thenReturnResult() {
        String query = "Which document has the details about Django?";
        Vector embedding = Vector.of(-0.34916985034942627f, 0.5338794589042664f, 0.43527376651763916f,
            -0.6110032200813293f, -0.17396864295005798f);
        SearchResults<Book> results = bookRepository.searchTop3ByEmbeddingNear(embedding, Similarity.of(.7));
        logger.info("Results found: {}", results.stream()
            .count());
        results.getContent()
            .forEach(book -> logger.info("Book: {}, yearPublished: {}", book.getContent()
                .getName(), book.getContent()
                .getYearPublished()));
    }

    @Test
    void whenSearchByYearPublishedAndEmbeddingNear_thenReturnResult() {
        //String query = "Which document has the details about Django?";
        Vector embedding = Vector.of(-0.34916985034942627f, 0.5338794589042664f, 0.43527376651763916f,
            -0.6110032200813293f, -0.17396864295005798f);

        var results = bookRepository.searchByYearPublishedAndEmbeddingNear("2022", embedding, Score.of(0.9));
        results.getContent()
            .forEach(content -> {
                var book = content.getContent();
                logger.info("Content: {}, Date: {}", book.getName(), book.getYearPublished());
            });
    }

    @Test
    void whenSearchByEmbeddingWithin_thenReturnResult() {
        //String query = "Which document has the details about Django?";
        Vector embedding = Vector.of(-0.34916985034942627f, 0.5338794589042664f,
            0.43527376651763916f, -0.6110032200813293f, -0.17396864295005798f);
        var results = bookRepository.searchByEmbeddingWithin(embedding, Range.closed(Similarity.of(0.7),
            Similarity.of(0.9)));
        logger.info("Results found: {}", results.stream().count());
        results.getContent()
            .forEach(book -> logger.info("Book: {}, yearPublished: {}", book.getContent()
                .getName(), book.getContent()
                .getYearPublished()));
    }
}
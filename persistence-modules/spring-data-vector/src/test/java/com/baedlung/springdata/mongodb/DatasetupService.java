package com.baedlung.springdata.mongodb;

import static org.springframework.data.mongodb.core.index.VectorIndex.SimilarityFunction.COSINE;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.SecureRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Vector;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.SearchIndexStatus;
import org.springframework.data.mongodb.core.index.VectorIndex;

import com.baeldung.springdata.mongodb.Book;
import com.baeldung.springdata.mongodb.MongoDbBookRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class DatasetupService {
    private final Logger logger = LoggerFactory.getLogger(DatasetupService.class);

    private MongoTemplate mongoTemplate;

    private MongoDbBookRepository mongoDbBookRepository;

    public DatasetupService(MongoTemplate mongoTemplate, MongoDbBookRepository mongoDbBookRepository) {
        this.mongoTemplate = mongoTemplate;
        this.mongoDbBookRepository = mongoDbBookRepository;
    }

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

                mongoDbBookRepository.save(doc);
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
            Thread.sleep(SLEEP_MS);
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

}

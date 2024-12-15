package com.baeldung.boot.json.convertfile.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoBulkWriteException;

@Service
public class ImportJsonService {
    private Logger log = LogManager.getLogger(this.getClass());

    @Autowired
    private MongoTemplate mongo;

    public String importTo(Class<?> type, List<String> jsonLines) {
        List<Document> mongoDocs = generateMongoDocs(jsonLines, type);
        String collection = type.getAnnotation(org.springframework.data.mongodb.core.mapping.Document.class)
            .value();
        int inserts = insertInto(collection, mongoDocs);
        return inserts + "/" + jsonLines.size();
    }

    public String importTo(String collection, List<String> jsonLines) {
        List<Document> mongoDocs = generateMongoDocs(jsonLines);
        int inserts = insertInto(collection, mongoDocs);
        return inserts + "/" + jsonLines.size();
    }

    private int insertInto(String collection, List<Document> mongoDocs) {
        try {
            Collection<Document> inserts = mongo.insert(mongoDocs, collection);
            return inserts.size();
        } catch (DataIntegrityViolationException e) {
            log.error("importing docs", e);
            if (e.getCause() instanceof MongoBulkWriteException) {
                return ((MongoBulkWriteException) e.getCause()).getWriteResult()
                    .getInsertedCount();
            }
            return 0;
        }
    }

    private List<Document> generateMongoDocs(List<String> lines) {
        return generateMongoDocs(lines, null);
    }

    private <T> List<Document> generateMongoDocs(List<String> lines, Class<T> type) {
        ObjectMapper mapper = new ObjectMapper();

        List<Document> docs = new ArrayList<>();
        for (String json : lines) {
            try {
                if (type != null) {
                    T v = mapper.readValue(json, type);
                    json = mapper.writeValueAsString(v);
                }
                docs.add(Document.parse(json));
            } catch (Throwable e) {
                log.error("parsing: " + json, e);
            }
        }
        return docs;
    }
}

package com.baeldung.boot.hashmap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import com.mongodb.BasicDBObject;

@SpringBootTest
@DirtiesContext
@TestPropertySource("/embedded.properties")
class MongoDbHashMapIntegrationTest {

    private static final Map<String, Object> MAP = new HashMap<>();
    private static final Set<Map<String, Object>> MAP_SET = new HashSet<>();

    @Autowired
    private MongoTemplate mongo;

    private void assertHasMongoId(Map<String, Object> saved) {
        assertNotNull(saved);
        assertNotNull(saved.get("_id"));
    }

    @BeforeAll
    static void init() {
        MAP.put("name", "Document A");
        MAP.put("number", 2);
        MAP.put("dynamic", true);

        Map<String, Object> otherMap = new HashMap<>();
        otherMap.put("name", "Other Document");
        otherMap.put("number", 22);

        MAP_SET.add(MAP);
        MAP_SET.add(otherMap);
    }

    @Test
    void whenUsingMap_thenInsertSucceeds() {
        Map<String, Object> saved = mongo.insert(MAP, "map-collection");

        assertHasMongoId(saved);
    }

    @Test
    void whenMapSet_thenInsertSucceeds() {
        Collection<Map<String, Object>> saved = mongo.insert(MAP_SET, "map-set");

        saved.forEach(this::assertHasMongoId);
        assertEquals(2, saved.size());
    }

    @Test
    void givenMap_whenDocumentConstructed_thenInsertSucceeds() {
        Document document = new Document(MAP);

        Document saved = mongo.insert(document, "doc-collection");

        assertHasMongoId(saved);
    }

    @Test
    void givenMap_whenBasicDbObjectConstructed_thenInsertSucceeds() {
        BasicDBObject dbObject = new BasicDBObject(MAP);

        BasicDBObject saved = mongo.insert(dbObject, "db-collection");

        assertHasMongoId(saved);
    }

    @Test
    void givenObjectList_whenDocumentSetConstructed_thenInsertSucceeds() {
        Map<String, List<Object>> input = new HashMap<>();
        List<Object> listOne = new ArrayList<>();
        listOne.add("Doc A");
        listOne.add(1);

        List<Object> listTwo = new ArrayList<>();
        listTwo.add("Doc B");
        listTwo.add(2);

        input.put("a", listOne);
        input.put("b", listTwo);

        Set<Document> docs = input.entrySet()
            .stream()
            .collect(HashSet<Document>::new, (set, entry) -> {
                Document document = new Document();

                document.put("_id", entry.getKey());
                Iterator<Object> iterator = entry.getValue()
                    .iterator();
                document.put("name", iterator.next());
                document.put("number", iterator.next());

                set.add(document);
            }, Set::addAll);

        Collection<Document> saved = mongo.insert(docs, "custom-set");
        saved.forEach(this::assertHasMongoId);
    }
}

package com.baeldung.boot.hashmap;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

    private static Map<String, Object> map = new HashMap<>();
    private static Collection<Map<String, Object>> mapList = new ArrayList<>();

    @Autowired
    private MongoTemplate mongo;

    @BeforeAll
    static void init() {
        map.put("prop1", "val");
        map.put("prop2", 2);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("prop1", "val2");
        map2.put("prop2", 22);

        mapList.add(map);
        mapList.add(map2);
    }

    @Test
    void whenUsingMap_thenInsertSucceeds() {
        Map<String, Object> insert = mongo.insert(map, "map-collection");

        assertNotNull(insert);
    }

    @Test
    void givenMap_whenDocumentConstructed_thenInsertSucceeds() {
        Document document = new Document(map);

        Document insert = mongo.insert(document, "doc-collection");

        assertNotNull(insert);
    }

    @Test
    void givenMap_whenBasicDbObjectConstructed_thenInsertSucceeds() {
        BasicDBObject dbObject = new BasicDBObject(map);

        BasicDBObject insert = mongo.insert(dbObject, "db-collection");

        assertNotNull(insert);
    }

    @Test
    void whenMapCollection_thenInsertSucceeds() {
        Collection<Map<String, Object>> insert = mongo.insert(mapList, "collection-map");

        assertNotNull(insert);
    }
}

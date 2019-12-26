package com.baeldung.jnosql.diana.document;

import org.jnosql.diana.api.Settings;
import org.jnosql.diana.api.document.*;
import org.jnosql.diana.mongodb.document.MongoDBDocumentConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.jnosql.diana.api.document.query.DocumentQueryBuilder.delete;
import static org.jnosql.diana.api.document.query.DocumentQueryBuilder.select;

public class DocumentApp {

    private static final String DB_NAME = "my-db";
    private static final String DOCUMENT_COLLECTION = "books";

    public static final String KEY_NAME = "_id";

    DocumentConfiguration configuration = new MongoDBDocumentConfiguration();

    public static void main(String... args) throws Exception {
        MongoDbInit.startMongoDb();

        DocumentApp app = new DocumentApp();
        app.process();

        MongoDbInit.stopMongoDb();
    }

    public void process() {

        Map<String, Object> map = new HashMap<>();
        map.put("mongodb-server-host-1", "localhost:27017");

        try (DocumentCollectionManagerFactory managerFactory = configuration.get(Settings.of(map));
             DocumentCollectionManager manager = managerFactory.get(DB_NAME);) {

            DocumentEntity documentEntity = DocumentEntity.of(DOCUMENT_COLLECTION);
            documentEntity.add(Document.of(KEY_NAME, "100"));
            documentEntity.add(Document.of("name", "JNoSQL in Action"));
            documentEntity.add(Document.of("pages", 620));

            //CREATE
            DocumentEntity saved = manager.insert(documentEntity);

            //READ
            DocumentQuery query = select().from(DOCUMENT_COLLECTION).where(KEY_NAME).eq("100").build();
            List<DocumentEntity> entities = manager.select(query);
            System.out.println(entities.get(0));

            //UPDATE
            saved.add(Document.of("author", "baeldung"));
            DocumentEntity updated = manager.update(saved);
            System.out.println(updated);

            //DELETE
            DocumentDeleteQuery deleteQuery = delete().from(DOCUMENT_COLLECTION).where(KEY_NAME).eq("100").build();
            manager.delete(deleteQuery);

            List<DocumentEntity> documentEntityList = manager.select(select().from(DOCUMENT_COLLECTION).build());
            System.out.println(documentEntityList);
        }
    }

}

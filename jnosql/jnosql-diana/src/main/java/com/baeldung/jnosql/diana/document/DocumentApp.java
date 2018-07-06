package com.baeldung.jnosql.diana.document;

import org.jnosql.diana.api.document.*;
import org.jnosql.diana.mongodb.document.MongoDBDocumentConfiguration;

import java.util.List;

import static org.jnosql.diana.api.document.query.DocumentQueryBuilder.delete;
import static org.jnosql.diana.api.document.query.DocumentQueryBuilder.select;

public class DocumentApp {

    public static final String DATABASE = "database";
    public static final String DOCUMENT_COLLECTION = "books";
    public static final String KEY_NAME = "_id";

    DocumentConfiguration configuration = new MongoDBDocumentConfiguration();

    public static void main(String... args) throws Exception {
        MongoDbInit.startMongoDb();

        DocumentApp app = new DocumentApp();
        app.process();

        MongoDbInit.stopMongoDb();
    }

    public void process() {

        try (DocumentCollectionManagerFactory documentManagerFactory = configuration.get();
             DocumentCollectionManager documentManager = documentManagerFactory.get(DATABASE);) {

            DocumentEntity documentEntity = DocumentEntity.of(DOCUMENT_COLLECTION);
            documentEntity.add(Document.of(KEY_NAME, "12"));
            documentEntity.add(Document.of("name", "JNoSQL in Action"));
            documentEntity.add(Document.of("pages", 620));

            //CREATE
            DocumentEntity saved = documentManager.insert(documentEntity);

            //READ
            DocumentQuery query = select().from(DOCUMENT_COLLECTION).where(KEY_NAME).eq("12").build();
            List<DocumentEntity> entities = documentManager.select(query);
            System.out.println(entities.get(0));

            //UPDATE
            saved.add(Document.of("author", "baeldung"));
            DocumentEntity updated = documentManager.update(saved);
            System.out.println(updated);

            //DELETE
            DocumentDeleteQuery deleteQuery = delete().from(DOCUMENT_COLLECTION).where(KEY_NAME).eq("12").build();
            documentManager.delete(deleteQuery);

            List<DocumentEntity> documentEntityList = documentManager.select(select().from(DOCUMENT_COLLECTION).build());
            System.out.println(documentEntityList);
        }
    }

}

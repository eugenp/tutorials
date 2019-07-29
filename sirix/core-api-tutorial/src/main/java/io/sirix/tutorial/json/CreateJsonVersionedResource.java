package io.sirix.tutorial.json;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.sirix.access.DatabaseConfiguration;
import org.sirix.access.Databases;
import org.sirix.access.ResourceConfiguration;
import org.sirix.access.trx.node.json.objectvalue.StringValue;

import io.sirix.tutorial.Constants;

public final class CreateJsonVersionedResource {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        createJsonDatabaseWithVersionedResource();
    }

    static void createJsonDatabaseWithVersionedResource() throws FileNotFoundException, IOException {
        final var databaseFile = Constants.SIRIX_DATA_LOCATION.resolve("json-database-versioned");
        final var dbConfig = new DatabaseConfiguration(databaseFile);
        Databases.createJsonDatabase(dbConfig);
        try (final var database = Databases.openJsonDatabase(databaseFile)) {
            database.createResource(ResourceConfiguration.newBuilder("resource")
                                                         .useTextCompression(false)
                                                         .useDeweyIDs(true)
                                                         .build());
            try (final var manager = database.openResourceManager("resource");
                 final var wtx = manager.beginNodeTrx()) {
                // Create sample document.
                JsonDocumentCreator.create(wtx);
                wtx.commit();
                
                // Add changes and commit a second revision.
                wtx.moveToDocumentRoot()
                   .getCursor()
                   .moveToFirstChild();
                wtx.insertObjectRecordAsFirstChild("revision2", new StringValue("yes"));
                wtx.commit();
                
                // Add changes and commit a third revision.
                wtx.moveToDocumentRoot()
                   .getCursor()
                   .moveToFirstChild()
                   .getCursor()
                   .moveToFirstChild();
                wtx.insertObjectRecordAsRightSibling("revision3", new StringValue("yes"));
                wtx.commit();
            }
        }
    }
}

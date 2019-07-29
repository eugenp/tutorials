package io.sirix.tutorial.json;

import java.nio.file.Files;

import org.sirix.access.DatabaseConfiguration;
import org.sirix.access.Databases;
import org.sirix.access.ResourceConfiguration;
import org.sirix.access.trx.node.json.objectvalue.StringValue;

import io.sirix.tutorial.Constants;

public final class CreateVersionedJsonResource {

    public static void main(String[] args) {
        createJsonDatabaseWithVersionedResource();
    }

    static void createJsonDatabaseWithVersionedResource() {
        final var databaseFile = Constants.SIRIX_DATA_LOCATION.resolve("json-database-versioned");

        if (Files.exists(databaseFile))
            Databases.removeDatabase(databaseFile);

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
                wtx.moveToDocumentRoot().trx().moveToFirstChild();
                wtx.insertObjectRecordAsFirstChild("revision2", new StringValue("yes"));
                wtx.commit();

                // Add changes and commit a third revision.
                wtx.moveToDocumentRoot().trx().moveToFirstChild().trx().moveToFirstChild();
                wtx.insertObjectRecordAsRightSibling("revision3", new StringValue("yes"));
                wtx.commit();
            }
        }

        System.out.println("Database with versioned resource created.");
    }
}

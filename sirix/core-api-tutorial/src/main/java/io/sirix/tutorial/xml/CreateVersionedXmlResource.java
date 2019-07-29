package io.sirix.tutorial.xml;

import java.nio.file.Files;

import org.sirix.access.DatabaseConfiguration;
import org.sirix.access.Databases;
import org.sirix.access.ResourceConfiguration;

import io.sirix.tutorial.Constants;

public final class CreateVersionedXmlResource {

    public static void main(String[] args) {
        createXmlDatabaseWithVersionedResource();
    }

    static void createXmlDatabaseWithVersionedResource() {
        final var databaseFile = Constants.SIRIX_DATA_LOCATION.resolve("xml-database-versioned");

        if (Files.exists(databaseFile))
            Databases.removeDatabase(databaseFile);

        final var dbConfig = new DatabaseConfiguration(databaseFile);
        Databases.createXmlDatabase(dbConfig);
        try (final var database = Databases.openXmlDatabase(databaseFile)) {
            final var resource = "resource";
            database.createResource(ResourceConfiguration.newBuilder(resource)
                                                         .useTextCompression(false)
                                                         .useDeweyIDs(true)
                                                         .build());
            try (final var manager = database.openResourceManager(resource);
                 final var wtx = manager.beginNodeTrx()) {
                XmlDocumentCreator.createVersioned(wtx);
            }
        }

        System.out.println("Database with versioned resource created.");
    }
}

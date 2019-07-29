package io.sirix.tutorial.xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.sirix.access.DatabaseConfiguration;
import org.sirix.access.Databases;
import org.sirix.access.ResourceConfiguration;
import org.sirix.service.xml.shredder.XmlShredder;

import io.sirix.tutorial.Constants;

public final class CreateXmlDatabase {

    private static final Path XML = Paths.get("src", "main", "resources", "xml");

    public static void main(String[] args) throws FileNotFoundException, IOException {
        createXmlDatabase();
    }

    static void createXmlDatabase() throws FileNotFoundException, IOException {
        final var pathToXmlFile = XML.resolve("orga.xml");
        final var databaseFile = Constants.SIRIX_DATA_LOCATION.resolve("xml-database");

        if (Files.exists(databaseFile))
            Databases.removeDatabase(databaseFile);

        final var dbConfig = new DatabaseConfiguration(databaseFile);
        Databases.createXmlDatabase(dbConfig);
        try (final var database = Databases.openXmlDatabase(databaseFile)) {
            database.createResource(ResourceConfiguration.newBuilder("resource")
                                                         .useTextCompression(false)
                                                         .useDeweyIDs(true)
                                                         .build());
            try (final var manager = database.openResourceManager("resource");
                 final var wtx = manager.beginNodeTrx();
                 final var fis = new FileInputStream(pathToXmlFile.toFile())) {
                wtx.insertSubtreeAsFirstChild(XmlShredder.createFileReader(fis));
                wtx.commit();
            }
        }

        System.out.println("Database with resource created.");
    }
}

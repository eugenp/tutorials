package io.sirix.tutorial.xml;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.sirix.access.DatabaseConfiguration;
import org.sirix.access.Databases;
import org.sirix.access.ResourceConfiguration;
import org.sirix.service.xml.serialize.XmlSerializer;
import org.sirix.service.xml.shredder.XmlShredder;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public final class CreateXmlDatabaseIntegrationTest {

    private static final Path XML_TEST_RESULT_DIRECTORY = Paths.get("src", "test", "resources", "xml-test-result-strings");

    private static final Path XML_DIRECTORY = Paths.get("src", "test", "resources", "xml");

    @Rule
    public TemporaryFolder tempDirectory = new TemporaryFolder();

    private Path databasePath;

    @Before
    public void setUp() {
        databasePath = Paths.get(tempDirectory.getRoot().getPath(), "sirix", "xml-database");
    }

    @Test
    public void createDatabaseResourceAndCheckWithSerialization() throws IOException {
        final var pathToXmlFile = XML_DIRECTORY.resolve("orga.xml");

        // Create an empty XML database.
        Databases.createXmlDatabase(new DatabaseConfiguration(databasePath));

        // Open the database.
        try (final var database = Databases.openXmlDatabase(databasePath)) {
            // Create a resource to store an XML-document.
            database.createResource(ResourceConfiguration.newBuilder("resource")
                    .useTextCompression(false)
                    .useDeweyIDs(true)
                    .build());

            // Import XML.
            try (final var manager = database.openResourceManager("resource");
                 final var wtx = manager.beginNodeTrx();
                 final var fileInputStream = new FileInputStream(pathToXmlFile.toFile())) {
                wtx.insertSubtreeAsFirstChild(XmlShredder.createFileReader(fileInputStream));
                wtx.commit();
            }

            // Serialize XML again and compare.
            try (final var manager = database.openResourceManager("resource");
                 final var outputStream = new ByteArrayOutputStream()) {
                final var serializer = XmlSerializer.newBuilder(manager, outputStream)
                                                    .emitXMLDeclaration()
                                                    .build();
                serializer.call();

                final var expectedXml = Files.readAllLines(XML_TEST_RESULT_DIRECTORY.resolve("orga.xml"),
                                                           StandardCharsets.UTF_8)
                                             .stream()
                                             .collect(Collectors.joining());
                assertEquals(expectedXml, outputStream.toString(StandardCharsets.UTF_8));
            }
        }
    }
}


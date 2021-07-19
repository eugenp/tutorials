package io.sirix.tutorial.xml;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sirix.access.DatabaseConfiguration;
import org.sirix.access.Databases;
import org.sirix.access.ResourceConfiguration;
import org.sirix.service.xml.serialize.XmlSerializer;
import org.sirix.service.xml.shredder.XmlShredder;

public final class CreateXmlDatabaseIntegrationTest {

    private static final Path XML_TEST_RESULT_DIRECTORY = Paths.get("src", "test", "resources", "xml-test-result-strings");

    private static final Path XML_DIRECTORY = Paths.get("src", "test", "resources", "xml");

    private static final String TMP_DIRECTORY = System.getProperty("java.io.tmpdir");

    private static final Path DATABASE_PATH = Paths.get(TMP_DIRECTORY, "sirix", "xml-database");

    @Before
    public void setUp() throws Exception {
        if (Files.exists(DATABASE_PATH))
            Databases.removeDatabase(DATABASE_PATH);
    }

    @After
    public void tearDown() throws Exception {
        if (Files.exists(DATABASE_PATH))
            Databases.removeDatabase(DATABASE_PATH);
    }

    @Test
    public void createDatabaseResourceAndCheckWithSerialization() throws IOException {
        final var pathToXmlFile = XML_DIRECTORY.resolve("orga.xml");

        // Create an empty XML database.
        Databases.createXmlDatabase(new DatabaseConfiguration(DATABASE_PATH));

        // Open the database.
        try (final var database = Databases.openXmlDatabase(DATABASE_PATH)) {
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


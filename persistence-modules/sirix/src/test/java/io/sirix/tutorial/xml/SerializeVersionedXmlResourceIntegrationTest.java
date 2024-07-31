package io.sirix.tutorial.xml;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.sirix.access.DatabaseConfiguration;
import org.sirix.access.Databases;
import org.sirix.api.xml.XmlResourceManager;
import org.sirix.service.xml.serialize.XmlSerializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class SerializeVersionedXmlResourceIntegrationTest {

    private static final Path XML_TEST_RESULT_DIRECTORY = Paths.get("src", "test", "resources", "xml-test-result-strings");

    @Rule
    public TemporaryFolder tempDirectory = new TemporaryFolder();

    private Path databasePath;

    @Before
    public void setUp() {
        databasePath = Paths.get(tempDirectory.getRoot().getPath(), "sirix", "xml-database");
    }

    @Test
    public void createVersionedResourceAndTestSerializations() throws IOException {
        Databases.createXmlDatabase(new DatabaseConfiguration(databasePath));

        try (final var database = Databases.openXmlDatabase(databasePath)) {
            VersionedXmlDocumentCreator.create(database);

            try (final var manager = database.openResourceManager("resource")) {
                serializeRevisionOneAndTwo(manager);
                serializeMostRecentRevision(manager);
                serializeAllRevisions(manager);
            }
        }
    }

    private static void serializeRevisionOneAndTwo(final XmlResourceManager manager) throws IOException {
        final var outputStream = new ByteArrayOutputStream();
        final var serializerForRevisionOneAndTwo =
            XmlSerializer.newBuilder(manager, outputStream, 1, 2).emitIDs()
                                                                 .prettyPrint()
                                                                 .build();
        serializerForRevisionOneAndTwo.call();
        final var expectedXml = Files.readAllLines(XML_TEST_RESULT_DIRECTORY.resolve("revision1And2.xml"),
                                                   StandardCharsets.UTF_8)
                                     .stream()
                                     .collect(Collectors.joining(System.lineSeparator()));
        assertEquals(expectedXml, outputStream.toString(StandardCharsets.UTF_8));
    }

    private static void serializeMostRecentRevision(final XmlResourceManager manager) throws IOException {
        final var outputStream = new ByteArrayOutputStream();
        final var serializerForMostRecentRevision =
            XmlSerializer.newBuilder(manager, outputStream).emitIDs()
                                                           .prettyPrint()
                                                           .build();
        serializerForMostRecentRevision.call();
        final var expectedXml = Files.readAllLines(XML_TEST_RESULT_DIRECTORY.resolve("mostRecentRevision.xml"),
                                                   StandardCharsets.UTF_8)
                                     .stream()
                                     .collect(Collectors.joining(System.lineSeparator()));
        assertEquals(expectedXml, outputStream.toString(StandardCharsets.UTF_8));
    }

    private static void serializeAllRevisions(final XmlResourceManager manager) throws IOException {
        final var outputStream = new ByteArrayOutputStream();
        final var serializerForAllRevisions =
            XmlSerializer.newBuilder(manager, outputStream, -1).emitIDs()
                                                               .prettyPrint()
                                                               .build();
        serializerForAllRevisions.call();
        final var expectedXml = Files.readAllLines(XML_TEST_RESULT_DIRECTORY.resolve("allRevisions.xml"),
                                                   StandardCharsets.UTF_8)
                                     .stream()
                                     .collect(Collectors.joining(System.lineSeparator()));
        assertEquals(expectedXml, outputStream.toString(StandardCharsets.UTF_8));
    }
}

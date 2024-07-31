package io.sirix.tutorial.json;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.sirix.access.DatabaseConfiguration;
import org.sirix.access.Databases;
import org.sirix.api.json.JsonResourceManager;
import org.sirix.service.json.serialize.JsonSerializer;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class SerializeVersionedJsonResourceIntegrationTest {

    private static final Path JSON_TEST_RESULT_DIRECTORY = Paths.get("src", "test", "resources", "json-test-result-strings");

    @Rule
    public TemporaryFolder tempDirectory = new TemporaryFolder();

    private Path databasePath;

    @Before
    public void setUp() {
        databasePath = Paths.get(tempDirectory.getRoot().getPath(), "sirix", "json-database");
    }

    @Test
    public void createVersionedResourceAndTestSerializations() throws IOException {
        Databases.createJsonDatabase(new DatabaseConfiguration(databasePath));

        try (final var database = Databases.openJsonDatabase(databasePath)) {
            VersionedJsonDocumentCreator.create(database);

            try (final var manager = database.openResourceManager("resource")) {
                serializeRevisionOneAndTwo(manager);
                serializeMostRecentRevision(manager);
                serializeAllRevisions(manager);
            }
        }
    }

    private static void serializeRevisionOneAndTwo(final JsonResourceManager manager) throws IOException {
        final var writer = new StringWriter();
        final var serializer = JsonSerializer.newBuilder(manager, writer, 1, 2).build();
        serializer.call();
        final var expectedJson = Files.readAllLines(JSON_TEST_RESULT_DIRECTORY.resolve("revision1And2.json"),
                                                    StandardCharsets.UTF_8)
                                      .stream()
                                      .collect(Collectors.joining());
        assertEquals(expectedJson, writer.toString());
    }

    private static void serializeMostRecentRevision(final JsonResourceManager manager) throws IOException {
        final var writer = new StringWriter();
        final var serializer = JsonSerializer.newBuilder(manager, writer).build();
        serializer.call();
        final var expectedJson = Files.readAllLines(JSON_TEST_RESULT_DIRECTORY.resolve("mostRecentRevision.json"),
                                                    StandardCharsets.UTF_8)
                                      .stream()
                                      .collect(Collectors.joining());
        assertEquals(expectedJson, writer.toString());
    }

    private static void serializeAllRevisions(final JsonResourceManager manager) throws IOException {
        final var writer = new StringWriter();
        final var serializer = JsonSerializer.newBuilder(manager, writer, -1).build();
        serializer.call();
        final var expectedJson = Files.readAllLines(JSON_TEST_RESULT_DIRECTORY.resolve("allRevisions.json"),
                                                    StandardCharsets.UTF_8)
                                      .stream()
                                      .collect(Collectors.joining());
        assertEquals(expectedJson, writer.toString());
    }
}

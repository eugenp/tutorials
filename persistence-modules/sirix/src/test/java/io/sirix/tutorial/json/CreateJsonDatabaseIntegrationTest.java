package io.sirix.tutorial.json;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.sirix.access.DatabaseConfiguration;
import org.sirix.access.Databases;
import org.sirix.access.ResourceConfiguration;
import org.sirix.service.json.serialize.JsonSerializer;
import org.sirix.service.json.shredder.JsonShredder;
import org.sirix.settings.VersioningType;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public final class CreateJsonDatabaseIntegrationTest {

    private static final Path JSON_DIRECTORY = Paths.get("src", "test", "resources", "json");

    private static final String COMPLEX_JSON =
        "{\"problems\":[{\"Diabetes\":[{\"medications\":[{\"medicationsClasses\":[{\"className\":[{\"associatedDrug\":[{\"name\":\"asprin\",\"dose\":\"\",\"strength\":\"500 mg\"}],\"associatedDrug#2\":[{\"name\":\"somethingElse\",\"dose\":\"\",\"strength\":\"500 mg\"}]}],\"className2\":[{\"associatedDrug\":[{\"name\":\"asprin\",\"dose\":\"\",\"strength\":\"500 mg\"}],\"associatedDrug#2\":[{\"name\":\"somethingElse\",\"dose\":\"\",\"strength\":\"500 mg\"}]}]}]}],\"labs\":[{\"missing_field\":\"missing_value\"}]}],\"Asthma\":[{}]}]}";

    @Rule
    public TemporaryFolder tempDirectory = new TemporaryFolder();

    private Path databasePath;

    @Before
    public void setUp() {
        databasePath = Paths.get(tempDirectory.getRoot().getPath(), "sirix", "json-database");
    }

    @Test
    public void createDatabaseResourceAndCheckWithSerialization() throws IOException {
        final var pathToJsonFile = JSON_DIRECTORY.resolve("complex1.json");

        // Create an empty JSON database.
        Databases.createJsonDatabase(new DatabaseConfiguration(databasePath));

        // Open the database.
        try (final var database = Databases.openJsonDatabase(databasePath)) {
            // Create a resource to store a JSON-document.
            database.createResource(ResourceConfiguration.newBuilder("resource")
                    .useTextCompression(false)
                    .useDeweyIDs(true)
                    .versioningApproach(VersioningType.DIFFERENTIAL)
                    .revisionsToRestore(3)
                    .buildPathSummary(true)
                    .build());

            // Import JSON.
            try (final var manager = database.openResourceManager("resource");
                 final var wtx = manager.beginNodeTrx()) {
                wtx.insertSubtreeAsFirstChild(JsonShredder.createFileReader(pathToJsonFile));
                wtx.commit();
            }

            // Serialize JSON again and compare.
            try (final var manager = database.openResourceManager("resource");
                 final Writer writer = new StringWriter()) {
                final var serializer = JsonSerializer.newBuilder(manager, writer).build();
                serializer.call();
                assertEquals(COMPLEX_JSON, writer.toString());
            }
        }
    }
}

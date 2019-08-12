package io.sirix.tutorial.json;

import static org.junit.Assert.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sirix.access.DatabaseConfiguration;
import org.sirix.access.Databases;
import org.sirix.access.ResourceConfiguration;
import org.sirix.api.json.JsonNodeReadOnlyTrx;
import org.sirix.axis.DescendantAxis;
import org.sirix.axis.IncludeSelf;
import org.sirix.axis.filter.FilterAxis;
import org.sirix.axis.filter.json.JsonNameFilter;
import org.sirix.service.json.shredder.JsonShredder;
import org.sirix.settings.VersioningType;

public final class JsonFilterIntegrationTest {

    private static final Path JSON_DIRECTORY = Paths.get("src", "test", "resources", "json");

    private static final String TMP_DIRECTORY = System.getProperty("java.io.tmpdir");

    private static final Path DATABASE_PATH = Paths.get(TMP_DIRECTORY, "sirix", "json-database");

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
    public void testFilter() {
        final var pathToJsonFile = JSON_DIRECTORY.resolve("complex1.json");

        // Create an empty JSON database.
        Databases.createJsonDatabase(new DatabaseConfiguration(DATABASE_PATH));

        // Open the database.
        try (final var database = Databases.openJsonDatabase(DATABASE_PATH)) {
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
                wtx.moveToDocumentRoot();

                int foundTimes = 0;

                final var axis = new DescendantAxis(wtx, IncludeSelf.YES);
                final var filter = new JsonNameFilter(wtx, "associatedDrug");

                for (var filterAxis = new FilterAxis<JsonNodeReadOnlyTrx>(axis, filter); filterAxis.hasNext();) {
                    filterAxis.next();
                    foundTimes++;
                }
                assertEquals(2, foundTimes);
            }
        }
    }
}

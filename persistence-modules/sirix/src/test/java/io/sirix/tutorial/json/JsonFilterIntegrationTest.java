package io.sirix.tutorial.json;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.sirix.access.DatabaseConfiguration;
import org.sirix.access.Databases;
import org.sirix.access.ResourceConfiguration;
import org.sirix.axis.DescendantAxis;
import org.sirix.axis.IncludeSelf;
import org.sirix.axis.filter.FilterAxis;
import org.sirix.axis.filter.json.JsonNameFilter;
import org.sirix.service.json.shredder.JsonShredder;
import org.sirix.settings.VersioningType;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public final class JsonFilterIntegrationTest {

    private static final Path JSON_DIRECTORY = Paths.get("src", "test", "resources", "json");

    @Rule
    public TemporaryFolder tempDirectory = new TemporaryFolder();

    private Path databasePath;

    @Before
    public void setUp() {
        databasePath = Paths.get(tempDirectory.getRoot().getPath(), "sirix", "json-database");
    }

    @Test
    public void testFilter() {
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
                wtx.moveToDocumentRoot();

                int foundTimes = 0;

                final var axis = new DescendantAxis(wtx, IncludeSelf.YES);
                final var filter = new JsonNameFilter(wtx, "associatedDrug");

                for (var filterAxis = new FilterAxis<>(axis, filter); filterAxis.hasNext();) {
                    filterAxis.next();
                    foundTimes++;
                }
                assertEquals(2, foundTimes);
            }
        }
    }
}

package io.sirix.tutorial.xml;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.sirix.access.DatabaseConfiguration;
import org.sirix.access.Databases;
import org.sirix.access.ResourceConfiguration;
import org.sirix.api.Axis;
import org.sirix.axis.ChildAxis;
import org.sirix.axis.DescendantAxis;
import org.sirix.axis.IncludeSelf;
import org.sirix.axis.NestedAxis;
import org.sirix.axis.concurrent.ConcurrentAxis;
import org.sirix.axis.filter.FilterAxis;
import org.sirix.axis.filter.xml.XdmNameFilter;
import org.sirix.service.xml.shredder.XmlShredder;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Note that this simple test shows, that the higher level XQuery-API is much more user-friendly, when chaining
 * axis is required.
 */
public class QueryXmlResourceWithConcurrentAxisIntegrationTest {

    private static final Path XML_DIRECTORY = Paths.get("src", "test", "resources", "xml");

    @Rule
    public TemporaryFolder tempDirectory = new TemporaryFolder();

    private Path databasePath;

    @Before
    public void setUp() {
        databasePath = Paths.get(tempDirectory.getRoot().getPath(), "sirix", "xml-database");
    }

    @Test
    public void createDatabaseAndXMarkResourceAndCheckQuery() throws IOException {
        final var pathToXmlFile = XML_DIRECTORY.resolve("regions.xml");

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

            try (final var manager = database.openResourceManager("resource");
                 final var firstConcurrRtx = manager.beginNodeReadOnlyTrx();
                 final var secondConcurrRtx = manager.beginNodeReadOnlyTrx();
                 final var thirdConcurrRtx = manager.beginNodeReadOnlyTrx();
                 final var firstRtx = manager.beginNodeReadOnlyTrx();
                 final var secondRtx = manager.beginNodeReadOnlyTrx();
                 final var thirdRtx = manager.beginNodeReadOnlyTrx()) {

                /* query: //regions/africa//location */
                final Axis axis =
                    new NestedAxis(
                        new NestedAxis(
                            new ConcurrentAxis<>(firstConcurrRtx,
                                new FilterAxis<>(new DescendantAxis(firstRtx, IncludeSelf.YES),
                                    new XdmNameFilter(firstRtx, "regions"))),
                            new ConcurrentAxis<>(secondConcurrRtx,
                                new FilterAxis<>(new ChildAxis(secondRtx),
                                    new XdmNameFilter(secondRtx, "africa")))),
                        new ConcurrentAxis<>(thirdConcurrRtx,
                            new FilterAxis<>(new DescendantAxis(thirdRtx, IncludeSelf.YES),
                                new XdmNameFilter(thirdRtx, "location"))));

                final var resultNumber = 55;

                for (int i = 0; i < resultNumber; i++) {
                    assertTrue(axis.hasNext());
                    axis.next();
                }
                assertFalse(axis.hasNext());
            }
        }
    }
}

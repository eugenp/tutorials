package io.sirix.tutorial.xml;

import org.brackit.xquery.atomic.QNm;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.sirix.access.DatabaseConfiguration;
import org.sirix.access.Databases;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public final class CreateVersionedXmlResourceIntegrationTest {

    @Rule
    public TemporaryFolder tempDirectory = new TemporaryFolder();

    private Path databasePath;

    @Before
    public void setUp() {
        databasePath = Paths.get(tempDirectory.getRoot().getPath(), "sirix", "xml-database");
    }

    @Test
    public void createVersionedResourceAndCheck() {
        Databases.createXmlDatabase(new DatabaseConfiguration(databasePath));

        try (final var database = Databases.openXmlDatabase(databasePath)) {
            VersionedXmlDocumentCreator.create(database);

            // Check first revision.
            try (final var manager = database.openResourceManager("resource");
                 final var rtx = manager.beginNodeReadOnlyTrx(1)) {
                rtx.moveToDocumentRoot().trx().moveToFirstChild().trx().moveToFirstChild();

                assertEquals("oops1", rtx.getValue());
            }

            // Check second revision.
            try (final var manager = database.openResourceManager("resource");
                 final var rtx = manager.beginNodeReadOnlyTrx(2)) {
                rtx.moveToDocumentRoot().trx().moveToFirstChild().trx().moveToFirstChild();

                assertEquals(new QNm("ns", "p", "a"), rtx.getName());
                assertEquals(14L, rtx.getNodeKey());

                rtx.moveToFirstChild();

                assertEquals("OOPS4!", rtx.getValue());
                assertEquals(15L, rtx.getNodeKey());

                rtx.moveToParent().trx().moveToRightSibling();

                assertEquals("oops1", rtx.getValue());
            }

            // Check second revision.
            try (final var manager = database.openResourceManager("resource");
                 final var rtx = manager.beginNodeReadOnlyTrx()) {
                rtx.moveToDocumentRoot().trx().moveToFirstChild().trx().moveToFirstChild();

                assertEquals(new QNm("ns", "p", "a"), rtx.getName());
                assertEquals(16L, rtx.getNodeKey());

                rtx.moveToFirstChild();

                assertEquals("OOPS4!", rtx.getValue());
                assertEquals(17L, rtx.getNodeKey());

                rtx.moveToParent().trx().moveToRightSibling();

                assertEquals(new QNm("ns", "p", "a"), rtx.getName());
                assertEquals(14L, rtx.getNodeKey());
            }
        }
    }
}

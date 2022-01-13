package io.sirix.tutorial.json;

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
import static org.junit.Assert.assertTrue;

public final class CreateVersionedJsonResourceIntegrationTest {

    @Rule
    public TemporaryFolder tempDirectory = new TemporaryFolder();

    private Path databasePath;

    @Before
    public void setUp() {
        databasePath = Paths.get(tempDirectory.getRoot().getPath(), "sirix", "json-database");
    }

    @Test
    public void createVersionedDatabaseAndCheckAllRevisions() {
        Databases.createJsonDatabase(new DatabaseConfiguration(databasePath));

        try (final var database = Databases.openJsonDatabase(databasePath)) {
            VersionedJsonDocumentCreator.create(database);

            // Check first revision.
            try (final var manager = database.openResourceManager("resource");
                final var rtx = manager.beginNodeReadOnlyTrx(1)) {
               rtx.moveToDocumentRoot().trx().moveToFirstChild().trx().moveToFirstChild();

               assertEquals(new QNm("foo"), rtx.getName());

               rtx.moveToFirstChild();

               assertTrue(rtx.isArray());
            }

            // Check second revision.
            try (final var manager = database.openResourceManager("resource");
                final var rtx = manager.beginNodeReadOnlyTrx(2)) {
               rtx.moveToDocumentRoot().trx().moveToFirstChild().trx().moveToFirstChild();

               assertEquals(new QNm("revision2"), rtx.getName());

               rtx.moveToFirstChild();

               assertEquals("yes", rtx.getValue());

               rtx.moveToParent().trx().moveToRightSibling();

               assertEquals(new QNm("foo"), rtx.getName());

               rtx.moveToFirstChild();

               assertTrue(rtx.isArray());
            }


            // Check final revision.
            try (final var manager = database.openResourceManager("resource");
                final var rtx = manager.beginNodeReadOnlyTrx()) {
               rtx.moveToDocumentRoot().trx().moveToFirstChild().trx().moveToFirstChild();

               assertEquals(new QNm("revision2"), rtx.getName());

               rtx.moveToFirstChild();

               assertEquals("yes", rtx.getValue());

               rtx.moveToParent().trx().moveToRightSibling();

               assertEquals(new QNm("revision3"), rtx.getName());

               rtx.moveToFirstChild();

               assertEquals("yes", rtx.getValue());
            }
        }
    }
}

package io.sirix.tutorial.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.brackit.xquery.atomic.QNm;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sirix.access.DatabaseConfiguration;
import org.sirix.access.Databases;

public final class CreateVersionedJsonResourceIntegrationTest {

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
    public void createVersionedDatabaseAndCheckAllRevisions() {
        Databases.createJsonDatabase(new DatabaseConfiguration(DATABASE_PATH));

        try (final var database = Databases.openJsonDatabase(DATABASE_PATH)) {
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

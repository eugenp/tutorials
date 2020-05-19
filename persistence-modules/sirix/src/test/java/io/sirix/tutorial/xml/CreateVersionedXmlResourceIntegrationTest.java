package io.sirix.tutorial.xml;

import static org.junit.Assert.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.brackit.xquery.atomic.QNm;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sirix.access.DatabaseConfiguration;
import org.sirix.access.Databases;

public final class CreateVersionedXmlResourceIntegrationTest {

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
    public void createVersionedResourceAndCheck() {
        Databases.createXmlDatabase(new DatabaseConfiguration(DATABASE_PATH));

        try (final var database = Databases.openXmlDatabase(DATABASE_PATH)) {
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

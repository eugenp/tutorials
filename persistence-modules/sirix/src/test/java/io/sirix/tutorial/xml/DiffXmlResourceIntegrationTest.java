package io.sirix.tutorial.xml;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sirix.access.DatabaseConfiguration;
import org.sirix.access.Databases;
import org.sirix.access.trx.node.HashType;
import org.sirix.diff.DiffFactory;
import org.sirix.diff.DiffFactory.DiffOptimized;
import org.sirix.diff.DiffFactory.DiffType;

import com.google.common.collect.ImmutableSet;

public class DiffXmlResourceIntegrationTest {
    private static final String TMP_DIRECTORY = System.getProperty("java.io.tmpdir");

    private static final Path DATABASE_PATH = Paths.get(TMP_DIRECTORY, "sirix", "xml-database");

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
    public void createVersionedResourceAndCheckDiffingOfRevisionOneAndThree() throws IOException {
        Databases.createXmlDatabase(new DatabaseConfiguration(DATABASE_PATH));

        try (final var database = Databases.openXmlDatabase(DATABASE_PATH)) {
            VersionedXmlDocumentCreator.create(database);

            try (final var manager = database.openResourceManager("resource");
                 final var rtxOnFirstRevision = manager.beginNodeReadOnlyTrx(1);
                 final var rtxOnThirdRevision = manager.beginNodeReadOnlyTrx(3)) {
                final var observer = new MyXmlDiffObserver();
                DiffFactory.invokeFullXmlDiff(
                    new DiffFactory.Builder<>(manager, 3, 1, manager.getResourceConfig().hashType == HashType.NONE
                        ? DiffOptimized.NO
                        : DiffOptimized.HASHED,
                    ImmutableSet.of(observer)).skipSubtrees(true));

                final var diffs = observer.getDiffTuples();

                assertEquals(2, diffs.size());

                final var firstDiffTuple = diffs.get(0);

                assertEquals(DiffType.INSERTED, firstDiffTuple.getDiff());

                final var secondDiffTuple = diffs.get(1);

                assertEquals(DiffType.INSERTED, secondDiffTuple.getDiff());
            }
        }
    }
}

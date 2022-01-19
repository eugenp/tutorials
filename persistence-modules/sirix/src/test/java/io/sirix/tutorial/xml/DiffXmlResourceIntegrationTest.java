package io.sirix.tutorial.xml;

import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.sirix.access.DatabaseConfiguration;
import org.sirix.access.Databases;
import org.sirix.access.trx.node.HashType;
import org.sirix.diff.DiffFactory;
import org.sirix.diff.DiffFactory.DiffOptimized;
import org.sirix.diff.DiffFactory.DiffType;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class DiffXmlResourceIntegrationTest {

    @Rule
    public TemporaryFolder tempDirectory = new TemporaryFolder();

    private Path databasePath;

    @Before
    public void setUp() {
        databasePath = Paths.get(tempDirectory.getRoot().getPath(), "sirix", "xml-database");
    }

    @Test
    public void createVersionedResourceAndCheckDiffingOfRevisionOneAndThree() {
        Databases.createXmlDatabase(new DatabaseConfiguration(databasePath));

        try (final var database = Databases.openXmlDatabase(databasePath)) {
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

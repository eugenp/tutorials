package io.sirix.tutorial.json;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.sirix.access.DatabaseConfiguration;
import org.sirix.access.Databases;
import org.sirix.api.ResourceManager;
import org.sirix.api.json.JsonNodeReadOnlyTrx;
import org.sirix.api.json.JsonNodeTrx;
import org.sirix.api.visitor.JsonNodeVisitor;
import org.sirix.api.visitor.VisitResult;
import org.sirix.api.visitor.VisitResultType;
import org.sirix.axis.temporal.PastAxis;
import org.sirix.axis.visitor.VisitorDescendantAxis;
import org.sirix.node.immutable.json.ImmutableObjectKeyNode;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class CreateVersionedJsonResourceAndQueryIntegrationTest {

    @Rule
    public TemporaryFolder tempDirectory = new TemporaryFolder();

    private Path databasePath;

    @Before
    public void setUp() {
        databasePath = Paths.get(tempDirectory.getRoot().getPath(), "sirix", "json-database");
    }

    @Test
    public void createVersionedResourceAndQueryWithTheVisitoDescendantAxis() {
        Databases.createJsonDatabase(new DatabaseConfiguration(databasePath));

        try (final var database = Databases.openJsonDatabase(databasePath)) {
            VersionedJsonDocumentCreator.create(database);

            try (final var manager = database.openResourceManager("resource");
                 // Starts a read only transaction on the most recent revision.
                 final var rtx = manager.beginNodeReadOnlyTrx()) {

                final var axis = VisitorDescendantAxis.newBuilder(rtx)
                                                      .includeSelf()
                                                      .visitor(new MyJsonNodeVisitor(manager, rtx))
                                                      .build();

                axis.forEach((unused) -> {});
            }
        }
    }

    private static final class MyJsonNodeVisitor implements JsonNodeVisitor {

        private final ResourceManager<JsonNodeReadOnlyTrx, JsonNodeTrx> manager;

        private final JsonNodeReadOnlyTrx trx;

        public MyJsonNodeVisitor(final ResourceManager<JsonNodeReadOnlyTrx, JsonNodeTrx> manager, final JsonNodeReadOnlyTrx rtx) {
            this.manager = manager;
            this.trx = rtx;
        }

        @Override
        public VisitResult visit(ImmutableObjectKeyNode node) {
            System.out.println("Object key node (most recent revision " + trx.getRevisionNumber() + "): " + node.getName());

            if (node.getNodeKey() == 24L) {
                final var pastAxis = new PastAxis<>(manager, trx);

                assertTrue(pastAxis.hasNext());
                pastAxis.next();

                assertFalse(pastAxis.hasNext());
            } else if (node.getNodeKey() == 25L) {
                final var pastAxis = new PastAxis<>(manager, trx);

                assertFalse(pastAxis.hasNext());
            }

            // Axis to iterate over the node in past revisions (if the node existed back then).
            final var pastAxis = new PastAxis<>(manager, trx);
            pastAxis.forEachRemaining((trx) ->
                System.out.println("Object key node in the past (revision " + trx.getRevisionNumber() + "): " + trx.getName()));

            return VisitResultType.CONTINUE;
        }
    }
}

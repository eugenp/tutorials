package io.sirix.tutorial.json;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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

public final class CreateVersionedJsonResourceAndQueryIntegrationTest {
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
    public void createVersionedResourceAndQueryWithTheVisitoDescendantAxis() throws IOException {
        Databases.createJsonDatabase(new DatabaseConfiguration(DATABASE_PATH));

        try (final var database = Databases.openJsonDatabase(DATABASE_PATH)) {
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

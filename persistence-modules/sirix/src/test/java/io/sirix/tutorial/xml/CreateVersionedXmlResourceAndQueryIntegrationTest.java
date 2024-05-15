package io.sirix.tutorial.xml;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.sirix.access.DatabaseConfiguration;
import org.sirix.access.Databases;
import org.sirix.api.ResourceManager;
import org.sirix.api.visitor.VisitResult;
import org.sirix.api.visitor.VisitResultType;
import org.sirix.api.visitor.XmlNodeVisitor;
import org.sirix.api.xml.XmlNodeReadOnlyTrx;
import org.sirix.api.xml.XmlNodeTrx;
import org.sirix.axis.NonStructuralWrapperAxis;
import org.sirix.axis.temporal.PastAxis;
import org.sirix.axis.visitor.VisitorDescendantAxis;
import org.sirix.node.immutable.xdm.ImmutableElement;
import org.sirix.node.immutable.xdm.ImmutableText;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class CreateVersionedXmlResourceAndQueryIntegrationTest {

    @Rule
    public TemporaryFolder tempDirectory = new TemporaryFolder();

    private Path databasePath;

    @Before
    public void setUp() {
        databasePath = Paths.get(tempDirectory.getRoot().getPath(), "sirix", "xml-database");
    }

    @Test
    public void createVersionedResourceAndQueryWithTheVisitToDescendantAxis() {
        Databases.createXmlDatabase(new DatabaseConfiguration(databasePath));

        try (final var database = Databases.openXmlDatabase(databasePath)) {
            VersionedXmlDocumentCreator.create(database);

            try (final var manager = database.openResourceManager("resource");
                 // Starts a read only transaction on the most recent revision.
                 final var rtx = manager.beginNodeReadOnlyTrx()) {

                final var axis = new NonStructuralWrapperAxis(
                    VisitorDescendantAxis.newBuilder(rtx)
                                         .includeSelf()
                                         .visitor(new MyXmlNodeVisitor(manager, rtx))
                                         .build());

                axis.forEach((unused) -> {});
            }
        }
    }

    private static final class MyXmlNodeVisitor implements XmlNodeVisitor {

        private final ResourceManager<XmlNodeReadOnlyTrx, XmlNodeTrx> manager;

        private final XmlNodeReadOnlyTrx trx;

        public MyXmlNodeVisitor(final ResourceManager<XmlNodeReadOnlyTrx, XmlNodeTrx> manager, final XmlNodeReadOnlyTrx rtx) {
            this.manager = manager;
            this.trx = rtx;
        }

        @Override
        public VisitResult visit(ImmutableElement node) {
            System.out.println("Element (most recent revision " + trx.getRevisionNumber() + "): " + node.getName());

            if (node.getNodeKey() == 14L) {
                final var pastAxis = new PastAxis<>(manager, trx);

                assertTrue(pastAxis.hasNext());
                pastAxis.next();

                assertFalse(pastAxis.hasNext());
            } else if (node.getNodeKey() == 16L) {
                final var pastAxis = new PastAxis<>(manager, trx);

                assertFalse(pastAxis.hasNext());
            }

            // Axis to iterate over the node in past revisions (if the node existed back then).
            final var pastAxis = new PastAxis<>(manager, trx);
            pastAxis.forEachRemaining((trx) ->
                System.out.println("Element in the past (revision " + trx.getRevisionNumber() + "): " + trx.getName()));

            for (int i = 0, attributes = trx.getAttributeCount(); i < attributes; i++) {
                trx.moveToAttribute(i);

                System.out.println("Attribute (most recent revision " + trx.getRevisionNumber() + "):"
                                   + trx.getName() + " ='" + trx.getValue() + "'");

                // Axis to iterate over the node in past revisions (if the node existed back then).
                final var pastAttributeAxis = new PastAxis<>(manager, trx);
                pastAttributeAxis.forEachRemaining((trx) ->
                    System.out.println("Attribute in the past (revision " + trx.getRevisionNumber() + "): "
                                       + trx.getName() + " ='" + trx.getValue() + "'"));

                trx.moveToParent();
            }

            return VisitResultType.CONTINUE;
        }

        @Override
        public VisitResult visit(ImmutableText node) {
            System.out.println("Text (most recent revision " + trx.getRevisionNumber() + "): " + node.getValue());

            // Axis to iterate over the node in past revisions (if the node existed back then).
            final var pastAxis = new PastAxis<>(manager, trx);
            pastAxis.forEachRemaining((trx) ->
                System.out.println("Text in the past (revision " + trx.getRevisionNumber() + "): " + trx.getValue()));

            return VisitResultType.CONTINUE;
        }
    }
}

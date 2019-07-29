package io.sirix.tutorial.xml;

import org.sirix.access.Databases;
import org.sirix.api.ResourceManager;
import org.sirix.api.visitor.VisitResult;
import org.sirix.api.visitor.VisitResultType;
import org.sirix.api.visitor.XmlNodeVisitor;
import org.sirix.api.xml.XmlNodeReadOnlyTrx;
import org.sirix.api.xml.XmlNodeTrx;
import org.sirix.axis.temporal.PastAxis;
import org.sirix.axis.visitor.VisitorDescendantAxis;
import org.sirix.node.immutable.xdm.ImmutableElement;
import org.sirix.node.immutable.xdm.ImmutableText;

import io.sirix.tutorial.Constants;

public final class CreateVersionedXmlResourceAndQuery {

    public static void main(String[] args) {
        CreateVersionedXmlResource.createXmlDatabaseWithVersionedResource();

        queryXmlDatabaseWithVersionedResource();
    }

    static void queryXmlDatabaseWithVersionedResource() {
        final var databaseFile = Constants.SIRIX_DATA_LOCATION.resolve("xml-database-versioned");

        try (final var database = Databases.openXmlDatabase(databaseFile)) {
            try (final var manager = database.openResourceManager("resource");
                 // Starts a read only transaction on the most recent revision.
                 final var rtx = manager.beginNodeReadOnlyTrx()) {

                final var axis = VisitorDescendantAxis.newBuilder(rtx)
                                                      .includeSelf()
                                                      .visitor(new MyXmlNodeVisitor(manager, rtx))
                                                      .build();

                axis.forEach((unused) -> {});
            }
        }

        System.out.println("Database with versioned resource created.");
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
            System.out.println("Element (most recent revision " + trx.getRevisionNumber() + "):" + node.getName());

            // Axis to iterate over the node in past revisions (if the node existed back then).
            final var pastAxis = new PastAxis<>(manager, trx);
            pastAxis.forEachRemaining((trx) ->
                System.out.println("Element in the past (revision " + trx.getRevisionNumber() + ": " + trx.getName()));

            return VisitResultType.CONTINUE;
        }

        @Override
        public VisitResult visit(ImmutableText node) {
            System.out.println("Text (most recent revision " + trx.getRevisionNumber() + "):" + node.getValue());

            // Axis to iterate over the node in past revisions (if the node existed back then).
            final var pastAxis = new PastAxis<>(manager, trx);
            pastAxis.forEachRemaining((trx) ->
                System.out.println("Text in the past (revision " + trx.getRevisionNumber() + ": " + trx.getValue()));

            return VisitResultType.CONTINUE;
        }
    }
}

package io.sirix.tutorial.xml;

import org.sirix.access.ResourceConfiguration;
import org.sirix.api.Database;
import org.sirix.api.xml.XmlResourceManager;

public final class VersionedXmlDocumentCreator {

    /**
     * Private Constructor, not used.
     */
    private VersionedXmlDocumentCreator() {
        throw new AssertionError("Not permitted to call constructor!");
    }

    public static void create(final Database<XmlResourceManager> database) {
        final var resource = "resource";
        database.createResource(ResourceConfiguration.newBuilder(resource)
                                                     .useTextCompression(false)
                                                     .useDeweyIDs(true)
                                                     .build());
        try (final var manager = database.openResourceManager(resource);
             final var wtx = manager.beginNodeTrx()) {
            XmlDocumentCreator.createVersioned(wtx);
        }
    }
}

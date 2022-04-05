package io.sirix.tutorial.json;

import org.sirix.access.ResourceConfiguration;
import org.sirix.api.Database;
import org.sirix.api.json.JsonResourceManager;

public final class VersionedJsonDocumentCreator {

    /**
     * Private Constructor, not used.
     */
    private VersionedJsonDocumentCreator() {
        throw new AssertionError("Not permitted to call constructor!");
    }

    public static void create(final Database<JsonResourceManager> database) {
        final var resource = "resource";
        database.createResource(ResourceConfiguration.newBuilder(resource)
                                                     .useTextCompression(false)
                                                     .useDeweyIDs(true)
                                                     .build());
        try (final var manager = database.openResourceManager(resource);
             final var wtx = manager.beginNodeTrx()) {
            // Create sample document.
            JsonDocumentCreator.createVersioned(wtx);
        }
    }
}

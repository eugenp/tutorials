package io.sirix.tutorial;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import org.sirix.access.Databases;
import org.sirix.api.json.JsonResourceManager;
import org.sirix.service.json.serialize.JsonSerializer;

public class SerializeJsonVersionedResource {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        CreateJsonVersionedResource.createJsonDatabaseWithVersionedResource();

        final var database = Databases.openJsonDatabase(Constants.SIRIX_DATA_LOCATION.resolve("json-database-versioned"));

        try (final var manager = database.openResourceManager("resource")) {
            serializeRevisionOneAndTwo(manager);

            serializeMostRecentRevision(manager);

            serializeAllRevisions(manager);
        }
    }

    private static void serializeRevisionOneAndTwo(final JsonResourceManager manager) {
        final var writer = new StringWriter();
        final var serializerForRevisionOneAndTwo = new JsonSerializer.Builder(manager, writer, 1, 2).build();
        serializerForRevisionOneAndTwo.call();
        System.out.println("Revision 1 and 2:");
        System.out.println(writer.toString());
    }

    private static void serializeMostRecentRevision(final JsonResourceManager manager) {
        final var writer = new StringWriter();
        final var serializerForMostRecentRevision = new JsonSerializer.Builder(manager, writer).build();
        serializerForMostRecentRevision.call();
        System.out.println("Most recent revision:");
        System.out.println(writer.toString());
    }

    private static void serializeAllRevisions(final JsonResourceManager manager) {
        final var writer = new StringWriter();
        final var serializerForAllRevisions = new JsonSerializer.Builder(manager, writer, -1).build();
        serializerForAllRevisions.call();
        System.out.println("All revisions:");
        System.out.println(writer.toString());
    }
}

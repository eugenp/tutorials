package io.sirix.tutorial.xml;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.sirix.access.DatabaseConfiguration;
import org.sirix.access.Databases;
import org.sirix.access.ResourceConfiguration;
import org.sirix.api.Axis;
import org.sirix.axis.ChildAxis;
import org.sirix.axis.DescendantAxis;
import org.sirix.axis.IncludeSelf;
import org.sirix.axis.NestedAxis;
import org.sirix.axis.concurrent.ConcurrentAxis;
import org.sirix.axis.filter.FilterAxis;
import org.sirix.axis.filter.xml.XdmNameFilter;
import org.sirix.service.xml.serialize.XmlSerializer;
import org.sirix.service.xml.shredder.XmlShredder;

import io.sirix.tutorial.Constants;

/**
 * Note that this simple example shows, that the higher level XQuery-API is much more user-friendly, when chaining
 * axis is required.
 */
public class QueryXmlResourceWithConcurrentAxis {

    private static final Path XML = Paths.get("src", "main", "resources", "xml");

    private static final Path DATABASE_PATH = Constants.SIRIX_DATA_LOCATION.resolve("xml-xmark-database");

    public static void main(String[] args) throws FileNotFoundException, IOException {
        createXmlDatabase();

        queryXmlDatabase();
    }

    static void createXmlDatabase() throws FileNotFoundException, IOException {
        final var pathToXmlFile = XML.resolve("10mb.xml");

        if (Files.exists(DATABASE_PATH))
            Databases.removeDatabase(DATABASE_PATH);

        final var dbConfig = new DatabaseConfiguration(DATABASE_PATH);
        Databases.createXmlDatabase(dbConfig);
        try (final var database = Databases.openXmlDatabase(DATABASE_PATH)) {
            database.createResource(ResourceConfiguration.newBuilder("resource")
                    .useTextCompression(false)
                    .useDeweyIDs(true)
                    .build());
            try (final var manager = database.openResourceManager("resource");
                 final var wtx = manager.beginNodeTrx();
                 final var fis = new FileInputStream(pathToXmlFile.toFile())) {
                wtx.insertSubtreeAsFirstChild(XmlShredder.createFileReader(fis));
                wtx.commit();
            }
        }
    }

    static void queryXmlDatabase() {
        try (final var database = Databases.openXmlDatabase(DATABASE_PATH);
             final var manager = database.openResourceManager("resource");
             final var firstConcurrRtx = manager.beginNodeReadOnlyTrx();
             final var secondConcurrRtx = manager.beginNodeReadOnlyTrx();
             final var thirdConcurrRtx = manager.beginNodeReadOnlyTrx();
             final var firstRtx = manager.beginNodeReadOnlyTrx();
             final var secondRtx = manager.beginNodeReadOnlyTrx();
             final var thirdRtx = manager.beginNodeReadOnlyTrx()) {

            /* query: //regions/africa//location */
            final Axis axis =
                new NestedAxis(
                    new NestedAxis(
                        new ConcurrentAxis<>(firstConcurrRtx,
                            new FilterAxis<>(new DescendantAxis(firstRtx, IncludeSelf.YES),
                                new XdmNameFilter(firstRtx, "regions"))),
                        new ConcurrentAxis<>(secondConcurrRtx,
                            new FilterAxis<>(new ChildAxis(secondRtx),
                                new XdmNameFilter(secondRtx, "africa")))),
                    new ConcurrentAxis<>(thirdConcurrRtx,
                        new FilterAxis<>(new DescendantAxis(thirdRtx, IncludeSelf.YES),
                            new XdmNameFilter(thirdRtx, "location"))));

            axis.forEach((unused) -> {
                final var outputStream = new ByteArrayOutputStream();
                final var serializer = XmlSerializer.newBuilder(manager, outputStream)
                                                    .emitIDs()
                                                    .prettyPrint()
                                                    .startNodeKey(axis.getTrx().getNodeKey())
                                                    .build();
                serializer.call();
                final var utf8Encoding = StandardCharsets.UTF_8.toString();
                try {
                    System.out.println(outputStream.toString(utf8Encoding));
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });
        }
    }
}

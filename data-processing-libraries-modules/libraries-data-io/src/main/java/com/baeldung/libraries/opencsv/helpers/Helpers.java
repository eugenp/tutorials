package com.baeldung.libraries.opencsv.helpers;

import com.baeldung.libraries.opencsv.Constants;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Helpers {

    // Write Files

    public static Path fileOutAllPath() throws URISyntaxException {
        URI uri = ClassLoader.getSystemResource(Constants.CSV_All).toURI();
        return Paths.get(uri);
    }

    public static Path fileOutBeanPath() throws URISyntaxException {
        URI uri = ClassLoader.getSystemResource(Constants.CSV_BEAN).toURI();
        return Paths.get(uri);
    }

    public static Path fileOutOnePath() throws URISyntaxException {
        URI uri = ClassLoader.getSystemResource(Constants.CSV_ONE).toURI();
        return Paths.get(uri);
    }

    // Read Files

    public static Path twoColumnCsvPath() throws URISyntaxException {
        URI uri = ClassLoader.getSystemResource(Constants.TWO_COLUMN_CSV).toURI();
        return Paths.get(uri);
    }

    public static Path fourColumnCsvPath() throws URISyntaxException {
        URI uri = ClassLoader.getSystemResource(Constants.FOUR_COLUMN_CSV).toURI();
        return Paths.get(uri);
    }

    public static Path namedColumnCsvPath() throws URISyntaxException {
        URI uri = ClassLoader.getSystemResource(Constants.NAMED_COLUMN_CSV).toURI();
        return Paths.get(uri);
    }

    public static String readFile(Path path) throws IOException {
        return IOUtils.toString(path.toUri());
    }


    // Dummy Data for Writing

    public static List<String[]> twoColumnCsvString() {
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{"ColA", "ColB"});
        list.add(new String[]{"A", "B"});
        return list;
    }

    public static List<String[]> fourColumnCsvString() {
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{"ColA", "ColB", "ColC", "ColD"});
        list.add(new String[]{"A", "B", "A", "B"});
        list.add(new String[]{"BB", "AB", "AA", "B"});
        return list;
    }

}

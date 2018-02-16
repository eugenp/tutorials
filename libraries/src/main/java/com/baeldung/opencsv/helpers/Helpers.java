package com.baeldung.opencsv.helpers;

import com.baeldung.opencsv.Constants;
import com.opencsv.CSVReader;

import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Helpers {

    public static void print(String msg) {
        System.out.println(msg);
    }

    public static void err(Exception ex) {
        System.out.println(Constants.GENERIC_EXCEPTION + " " + ex);
    }

    public static CSVReader csv(Path path) {
        CSVReader csvReader = null;
        Reader reader = null;
        try {
            reader = Files.newBufferedReader(path);
        } catch (Exception ex) {
            err(ex);
        } finally {
            try {
                csvReader = new CSVReader(reader);
                if (reader != null) reader.close();
            } catch (Exception ex) {
                err(ex);
            }
        }
        return csvReader;
    }

    public static List<String[]> fetchTwoColumnCsvString() {
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{"ColA", "ColB"});
        list.add(new String[]{"ColA", "ColB"});
        return list;
    }

    public static List<String[]> fetchFourColumnCsvString() {
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{"ColA", "ColB"});
        list.add(new String[]{"ColA", "ColB"});
        return list;
    }


    public static Path getFourColumnCsvPath() throws URISyntaxException {
        URI uri = ClassLoader.getSystemResource(Constants.FOUR_COLUMN_CSV).toURI();
        return Paths.get(uri);
    }

    public static Path getTwoColumnCsv()  throws URISyntaxException {
        URI uri = ClassLoader.getSystemResource(Constants.TWO_COLUMN_CSV).toURI();
        return Paths.get(uri);
    }

    public static Path getNamedColumnCsv()  throws URISyntaxException {
        URI uri = ClassLoader.getSystemResource(Constants.NAMED_COLUMN_CSV).toURI();
        return Paths.get(uri);
    }


}

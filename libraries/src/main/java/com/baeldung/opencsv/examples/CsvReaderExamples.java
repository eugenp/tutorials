package com.baeldung.opencsv.examples;

import com.baeldung.opencsv.helpers.Helpers;
import com.opencsv.CSVReader;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CsvReaderExamples {

    public static List<String[]> readAll(Reader reader) {
        CSVReader csvReader = new CSVReader(reader);
        List<String[]> list = new ArrayList<>();
        try {
            list = csvReader.readAll();
            reader.close();
            csvReader.close();
        } catch (Exception ex) {
            Helpers.err(ex);
        }
        return list;
    }

    public static List<String[]> oneByOne(Reader reader) {
        List<String[]> list = new ArrayList<>();
        try {
            CSVReader csvReader = new CSVReader(reader);
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                list.add(line);
            }
            reader.close();
            csvReader.close();
        } catch (Exception ex) {
            Helpers.err(ex);
        }
        return list;
    }

}

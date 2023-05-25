package com.baeldung.libraries.opencsv;

import com.baeldung.libraries.opencsv.beans.CsvBean;
import com.baeldung.libraries.opencsv.beans.NamedColumnBean;
import com.baeldung.libraries.opencsv.beans.SimplePositionBean;
import com.baeldung.libraries.opencsv.examples.sync.BeanExamples;
import com.baeldung.libraries.opencsv.examples.sync.CsvReaderExamples;
import com.baeldung.libraries.opencsv.examples.sync.CsvWriterExamples;
import com.baeldung.libraries.opencsv.helpers.Helpers;

import java.nio.file.Path;
import java.util.List;

public class Application {

    // CSV Reader Examples
    public static List<String[]> readLineByLineSyncExample() throws Exception {
        Path path = Helpers.twoColumnCsvPath();
        return CsvReaderExamples.readLineByLine(path);
    }

    public static List<String[]> readAllLinesSyncExample() throws Exception {
        Path path = Helpers.twoColumnCsvPath();
        return CsvReaderExamples.readAllLines(path);
    }

    // CSV Writer Examples
    public static String writeLineByLineSyncExample() throws Exception {
        Path path = Helpers.fileOutOnePath();
        return CsvWriterExamples.writeLineByLine(Helpers.fourColumnCsvString(), path);
    }

    public static String writeAllLinesSyncExample() throws Exception {
        Path path = Helpers.fileOutAllPath();
        return CsvWriterExamples.writeAllLines(Helpers.fourColumnCsvString(), path);
    }

    // Bean Examples
    public static List<CsvBean> simpleSyncPositionBeanExample() throws Exception {
        Path path = Helpers.twoColumnCsvPath();
        return BeanExamples.beanBuilderExample(path, SimplePositionBean.class);
    }

    public static List<CsvBean> namedSyncColumnBeanExample() throws Exception {
        Path path = Helpers.namedColumnCsvPath();
        return BeanExamples.beanBuilderExample(path, NamedColumnBean.class);
    }

    public static String writeSyncCsvFromBeanExample() throws Exception {
        Path path = Helpers.fileOutBeanPath();
        return BeanExamples.writeCsvFromBean(path);
    }

    public static void main(String[] args) {
        try {
            simpleSyncPositionBeanExample();
            namedSyncColumnBeanExample();
            writeSyncCsvFromBeanExample();
            readLineByLineSyncExample();
            readAllLinesSyncExample();
            writeLineByLineSyncExample();
            writeAllLinesSyncExample();
        } catch (Exception e) {
            throw new RuntimeException("Error during csv processing", e);
        }
    }
}

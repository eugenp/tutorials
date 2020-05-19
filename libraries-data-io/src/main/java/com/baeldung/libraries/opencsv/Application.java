package com.baeldung.libraries.opencsv;

import com.baeldung.libraries.opencsv.beans.NamedColumnBean;
import com.baeldung.libraries.opencsv.beans.SimplePositionBean;
import com.baeldung.libraries.opencsv.examples.sync.BeanExamples;
import com.baeldung.libraries.opencsv.examples.sync.CsvReaderExamples;
import com.baeldung.libraries.opencsv.examples.sync.CsvWriterExamples;
import com.baeldung.libraries.opencsv.helpers.Helpers;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

public class Application {

        /*
         *  Bean Examples.
         */

    public static String simpleSyncPositionBeanExample() {
        Path path = null;
        try {
            path = Helpers.twoColumnCsvPath();
        } catch (Exception ex) {
            Helpers.err(ex);
        }
        return BeanExamples.beanBuilderExample(path, SimplePositionBean.class).toString();
    }

    public static String namedSyncColumnBeanExample() {
        Path path = null;
        try {
            path = Helpers.namedColumnCsvPath();
        } catch (Exception ex) {
            Helpers.err(ex);
        }
        return BeanExamples.beanBuilderExample(path, NamedColumnBean.class).toString();
    }

    public static String writeSyncCsvFromBeanExample() {
        Path path = null;
        try {
            path = Helpers.fileOutBeanPath();
        } catch (Exception ex) {
            Helpers.err(ex);
        }
        return BeanExamples.writeCsvFromBean(path);
    }

        /*
         *  CSV Reader Examples.
         */

    public static String oneByOneSyncExample() {
        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Helpers.twoColumnCsvPath());
        } catch (Exception ex) {
            Helpers.err(ex);
        }
        return CsvReaderExamples.oneByOne(reader).toString();
    }

    public static String readAllSyncExample() {
        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Helpers.twoColumnCsvPath());
        } catch (Exception ex) {
            Helpers.err(ex);
        }
        return CsvReaderExamples.readAll(reader).toString();
    }

         /*
         *  CSV Writer Examples.
         */


    public static String csvWriterSyncOneByOne() {
        Path path = null;
        try {
            path = Helpers.fileOutOnePath();
        } catch (Exception ex) {
            Helpers.err(ex);
        }
        return CsvWriterExamples.csvWriterOneByOne(Helpers.fourColumnCsvString(), path);
    }

    public static String csvWriterSyncAll() {
        Path path = null;
        try {
            path = Helpers.fileOutAllPath();
        } catch (Exception ex) {
            Helpers.err(ex);
        }
        return CsvWriterExamples.csvWriterAll(Helpers.fourColumnCsvString(), path);
    }

    public static void main(String[] args) {
        simpleSyncPositionBeanExample();
        namedSyncColumnBeanExample();
        writeSyncCsvFromBeanExample();
        oneByOneSyncExample();
        readAllSyncExample();
        csvWriterSyncOneByOne();
        csvWriterSyncAll();
    }
}

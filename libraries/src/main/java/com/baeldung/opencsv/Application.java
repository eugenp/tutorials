package com.baeldung.opencsv;

import com.baeldung.opencsv.helpers.Helpers;
import com.baeldung.opencsv.examples.BeanExamples;
import com.baeldung.opencsv.examples.CsvReaderExamples;
import com.baeldung.opencsv.examples.CsvWriterExamples;
import com.baeldung.opencsv.beans.NamedColumnBean;
import com.baeldung.opencsv.beans.SimplePositionBean;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

public class Application {

        /*
         *  Bean Examples.
         */

    public static String simplePositionBeanExample() {
        Path path = null;
        try {
            path = Helpers.twoColumnCsvPath();
        } catch (Exception ex) {
            Helpers.err(ex);
        }
        return BeanExamples.beanBuilderExample(path, SimplePositionBean.class).toString();
    }

    public static String namedColumnBeanExample() {
        Path path = null;
        try {
            path = Helpers.namedColumnCsvPath();
        } catch (Exception ex) {
            Helpers.err(ex);
        }
        return BeanExamples.beanBuilderExample(path, NamedColumnBean.class).toString();
    }

    public static String writeCsvFromBeanExample() {
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

    public static String oneByOneExample() {
        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Helpers.twoColumnCsvPath());
        } catch (Exception ex) {
            Helpers.err(ex);
        }
        return CsvReaderExamples.oneByOne(reader).toString();
    }

    public static String readAllExample() {
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


    public static String csvWriterOneByOne() {
        Path path = null;
        try {
            path = Helpers.fileOutOnePath();
        } catch (Exception ex) {
            Helpers.err(ex);
        }
        return CsvWriterExamples.csvWriterOneByOne(Helpers.fourColumnCsvString(), path);
    }

    public static String csvWriterAll() {
        Path path = null;
        try {
            path = Helpers.fileOutAllPath();
        } catch (Exception ex) {
            Helpers.err(ex);
        }
        return CsvWriterExamples.csvWriterAll(Helpers.fourColumnCsvString(), path);
    }

    public static void main(String[] args) {
        simplePositionBeanExample();
        namedColumnBeanExample();
        writeCsvFromBeanExample();
        oneByOneExample();
        readAllExample();
        csvWriterOneByOne();
        csvWriterAll();
    }
}

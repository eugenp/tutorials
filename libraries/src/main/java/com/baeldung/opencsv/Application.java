package com.baeldung.opencsv;

import com.baeldung.opencsv.beans.NamedColumnBean;
import com.baeldung.opencsv.beans.SimplePositionBean;
import com.baeldung.opencsv.examples.BeanExamples;
import com.baeldung.opencsv.examples.CsvReaderExamples;
import com.baeldung.opencsv.examples.CsvWriterExamples;
import com.baeldung.opencsv.helpers.Helpers;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Application {

        /*
         *  Bean Examples.
         */

    public static CompletableFuture simpleAsyncPositionBeanExample() {
        Path path = null;
        try {
            path = Helpers.twoColumnCsvPath();
        } catch (Exception ex) {
            Helpers.err(ex);
        }
        CompletableFuture result = BeanExamples.beanBuilderExample(path, SimplePositionBean.class);
        result.whenCompleteAsync((suc, err) -> {
            Helpers.print(suc.toString());
        });
        return result;

    }

    public static CompletableFuture namedAsyncColumnBeanExample() {
        Path path = null;
        try {
            path = Helpers.namedColumnCsvPath();
        } catch (Exception ex) {
            Helpers.err(ex);
        }
        CompletableFuture result = BeanExamples.beanBuilderExample(path, NamedColumnBean.class);
        result.whenCompleteAsync((suc, err) -> {
            Helpers.print(suc.toString());
        });
        return result;
    }

    public static CompletableFuture writeAsyncCsvUsingBeanBuilder() {
        Path path = null;
        try {
            path = Helpers.fileOutBeanPath();
        } catch (Exception ex) {
            Helpers.err(ex);
        }
        CompletableFuture<List<String[]>> result = BeanExamples.writeCsvUsingBeanBuilder(path);
        result.whenCompleteAsync((suc, err) -> {
            Helpers.print(suc.toString());
        });
        return result;
    }

        /*
         *  CSV Reader Examples.
         */

    public static CompletableFuture oneByOneAsyncExample() {
        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Helpers.twoColumnCsvPath());
        } catch (Exception ex) {
            Helpers.err(ex);
        }
        CompletableFuture<List<String[]>> result = CsvReaderExamples.oneByOne(reader);
        result.whenCompleteAsync((suc, err) -> {
            Helpers.print(suc.toString());
        });
        return result;
    }

    public static CompletableFuture readAllAsyncExample() {
        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Helpers.twoColumnCsvPath());
        } catch (Exception ex) {
            Helpers.err(ex);
        }
        CompletableFuture<List<String[]>> result = CsvReaderExamples.readAll(reader);
        result.whenCompleteAsync((suc, err) -> {
            Helpers.print(suc.toString());
        });
        return result;
    }

         /*
         *  CSV Writer Examples.
         */

    public static CompletableFuture csvWriterAsyncOneByOne() {
        Path path = null;
        try {
            path = Helpers.fileOutOnePath();
        } catch (Exception ex) {
            Helpers.err(ex);
        }

        CompletableFuture<List<String[]>> result = CsvWriterExamples.csvWriterOneByOne(Helpers.fourColumnCsvString(), path);
        result.whenCompleteAsync((suc, err) -> {
            Helpers.print(suc.toString());
        });
        return result;
    }

    public static CompletableFuture csvWriterAsyncAll() {
        Path path = null;
        try {
            path = Helpers.fileOutAllPath();
        } catch (Exception ex) {
            Helpers.err(ex);
        }
        CompletableFuture<List<String[]>> result = CsvWriterExamples.csvWriterAll(Helpers.fourColumnCsvString(), path);
        result.whenCompleteAsync((suc, err) -> {
            Helpers.print(suc.toString());
        });
        return result;
    }

    public static void main(String[] args) {

        simpleAsyncPositionBeanExample();
        namedAsyncColumnBeanExample();
        writeAsyncCsvUsingBeanBuilder();
        oneByOneAsyncExample();
        readAllAsyncExample();
        csvWriterAsyncOneByOne();
        csvWriterAsyncAll();

    }
}

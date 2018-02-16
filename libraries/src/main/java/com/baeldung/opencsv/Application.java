package com.baeldung.opencsv;

import com.baeldung.opencsv.helpers.Helpers;
import com.baeldung.opencsv.opencsv.BeanExamples;
import com.baeldung.opencsv.opencsv.CsvReaderExamples;
import com.baeldung.opencsv.pojos.NamedColumnBean;
import com.baeldung.opencsv.pojos.SimplePositionBean;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Application {

    /*
     *  Bean Examples.
     */
    public static CompletableFuture simplePositionBeanExample() {
        Path path = null;
        try {
            path = Helpers.getTwoColumnCsv();
        } catch (Exception ex) {
            Helpers.err(ex);
        }
        CompletableFuture result = BeanExamples.beanBuilderExample(path, SimplePositionBean.class);
        result.whenCompleteAsync((suc, err) -> {
            Helpers.print(suc.toString());
        });
        return result;

    }

    public static CompletableFuture namedColumnBeanExample() {
        Path path = null;
        try {
            path = Helpers.getTwoColumnCsv();
        } catch (Exception ex) {
            Helpers.err(ex);
        }
        CompletableFuture result = BeanExamples.beanBuilderExample(path, NamedColumnBean.class);
        result.whenCompleteAsync((suc, err) -> {
            Helpers.print(suc.toString());
        });
        return result;

    }


        /*
         *  Non-Bean Examples.
         */

    public static CompletableFuture oneByOneExample() {

        Reader reader = null;

        try {
            reader = Files.newBufferedReader(Helpers.getTwoColumnCsv());
        } catch (Exception ex) {
            Helpers.err(ex);
        }

        CompletableFuture<List<String[]>> result = CsvReaderExamples.oneByOne(reader);
        result.whenCompleteAsync((suc, err) -> {
            Helpers.print(suc.toString());
        });
        return result;

    }

    public static CompletableFuture readAllExample() {

        Reader reader = null;

        try {
            reader = Files.newBufferedReader(Helpers.getTwoColumnCsv());
        } catch (Exception ex) {
            Helpers.err(ex);
        }

        CompletableFuture<List<String[]>> result = CsvReaderExamples.readAll(reader);
        result.whenCompleteAsync((suc, err) -> {
            Helpers.print(suc.toString());
        });
        return result;

    }

    public static void main(String[] args) {
        simplePositionBeanExample();
        namedColumnBeanExample();
        oneByOneExample();
        readAllExample();
    }
}
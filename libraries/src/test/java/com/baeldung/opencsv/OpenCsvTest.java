package com.baeldung.opencsv;

import org.junit.Test;

import java.util.List;
public class OpenCsvTest {

    private Object testCsv(Object result) {
        assert (result != null);
        assert (result instanceof List);
        assert (((List) result).size() > 1);
        return result;
    }

    @Test
    public void positionExampleTest() {
        com.baeldung.opencsv.Application.simplePositionBeanExample().thenApplyAsync(result -> {
            return testCsv(result);
        });
    }

    @Test
    public void namedColumnExampleTest() {
        com.baeldung.opencsv.Application.namedColumnBeanExample().thenApplyAsync(result -> {
            return testCsv(result);
        });
    }

    @Test
    public void oneByOneExampleTest() {
        com.baeldung.opencsv.Application.oneByOneExample().thenApplyAsync(result -> {
            return testCsv(result);
        });
    }

    @Test
    public void readAllExampleTest() {
        com.baeldung.opencsv.Application.readAllExample().thenApplyAsync(result -> {
            return testCsv(result);
        });
    }

}




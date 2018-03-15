package com.baeldung.opencsv;

import com.baeldung.opencsv.helpers.Helpers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OpenCsvTest {

    private Object testReadCsv(Object result) {
        assert (result != null);
        assert (result instanceof String);
        assert (!((String) result).isEmpty());
        System.out.println(result);
        return result;
    }

    private Object testWriteCsv(Object result) {
        assert (result instanceof String);
        //assert (result.equals(Helpers.fetchFourColumnCsvString().toString()));
        assert (!((String) result).isEmpty());
        //System.out.println(result);
        return result;
    }

    @Before
    public void setup() {
    }

    @Test
    public void positionExampleTest() {
        Application.simpleAsyncPositionBeanExample().thenApplyAsync(result -> {
            return testReadCsv(result);
        });
    }

    @Test
    public void namedColumnExampleTest() {
        Application.namedAsyncColumnBeanExample().thenApplyAsync(result -> {
            return testReadCsv(result);
        });
    }

    @Test
    public void writeCsvUsingBeanBuilderTest() {
        Application.writeAsyncCsvUsingBeanBuilder().thenApplyAsync(result -> {
            return testWriteCsv(result);
        });
    }

    @Test
    public void oneByOneExampleTest() {
        Application.oneByOneAsyncExample().thenApplyAsync(result -> {
            return testReadCsv(result);
        });
    }

    @Test
    public void readAllExampleTest() {
        Application.readAllAsyncExample().thenApplyAsync(result -> {
            return testReadCsv(result);
        });
    }

    @Test
    public void csvWriterOneByOneTest() {
        Application.csvWriterAsyncOneByOne().thenApplyAsync(result -> {
            return testWriteCsv(result);
        });
    }
    @Test
    public void csvWriterAllTest() {
        Application.csvWriterAsyncAll().thenApplyAsync(result -> {
            return testWriteCsv(result);
        });
    }

    @After
    public void close() {
    }
}
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
        assert (!((String) result).isEmpty());
        return result;
    }

    @Before
    public void setup() {
    }

    @Test
    public void positionExampleTest() {
        testReadCsv(Application.simplePositionBeanExample());
    }

    @Test
    public void namedColumnExampleTest() {
        testReadCsv(Application.namedColumnBeanExample());
    }

    @Test
    public void writeCsvUsingBeanBuilderTest() {
        testWriteCsv(Application.writeCsvFromBeanExample());
    }

    @Test
    public void oneByOneExampleTest() {
        testReadCsv(Application.oneByOneExample());
    }

    @Test
    public void readAllExampleTest() {
        testReadCsv(Application.readAllExample());
    }

    @Test
    public void csvWriterOneByOneTest() {
        testWriteCsv(Application.csvWriterOneByOne());
    }

    @Test
    public void csvWriterAllTest() {
        testWriteCsv(Application.csvWriterAll());
    }

    @After
    public void close() {
    }
}
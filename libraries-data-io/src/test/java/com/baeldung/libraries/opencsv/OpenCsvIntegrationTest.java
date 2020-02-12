package com.baeldung.libraries.opencsv;

import com.baeldung.libraries.opencsv.helpers.Helpers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OpenCsvIntegrationTest {

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
        testReadCsv(Application.simpleSyncPositionBeanExample());
    }

    @Test
    public void namedColumnExampleTest() {
        testReadCsv(Application.namedSyncColumnBeanExample());
    }

    @Test
    public void writeCsvUsingBeanBuilderTest() {
        testWriteCsv(Application.writeSyncCsvFromBeanExample());
    }

    @Test
    public void oneByOneExampleTest() {
        testReadCsv(Application.oneByOneSyncExample());
    }

    @Test
    public void readAllExampleTest() {
        testReadCsv(Application.readAllSyncExample());
    }

    @Test
    public void csvWriterOneByOneTest() {
        testWriteCsv(Application.csvWriterSyncOneByOne());
    }

    @Test
    public void csvWriterAllTest() {
        testWriteCsv(Application.csvWriterSyncAll());
    }

    @After
    public void close() {
    }
}
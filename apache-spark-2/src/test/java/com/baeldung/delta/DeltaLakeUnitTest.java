package com.baeldung.delta;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeltaLakeUnitTest {

    private static SparkSession spark;
    private static String tablePath;

    @BeforeAll
    static void setUp() {
        spark = DeltaLake.createSession();
        tablePath = DeltaLake.preparePeopleTable(spark);
    }

    @AfterAll
    static void tearDown() {
        try {
            DeltaLake.cleanupPeopleTable(spark);
        } finally {
            DeltaLake.stopSession(spark);
        }
    }

    @Test
    void givenDeltaLake_whenUsingDeltaFormat_thenPrintAndValidate() {
        Dataset<Row> df = spark.sql("DESCRIBE DETAIL people");
        df.show(false);

        Row row = df.first();
        assertEquals("file:"+tablePath, row.getAs("location"));
        assertEquals("delta", row.getAs("format"));
        assertTrue(row.<Long>getAs("numFiles") >= 1);
    }
}

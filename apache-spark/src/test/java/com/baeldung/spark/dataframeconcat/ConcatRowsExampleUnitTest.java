package com.baeldung.spark.dataframeconcat;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.jupiter.api.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ConcatRowsExampleUnitTest {

    private static SparkSession spark;
    private Dataset<Row> df1;
    private Dataset<Row> df2;

    @BeforeAll
    static void setupClass() {
        spark = SparkSession.builder()
            .appName("Row-wise Concatenation Test")
            .master("local[*]")
            .getOrCreate();
    }

    @BeforeEach
    void setup() {
        df1 = spark.createDataFrame(
            Arrays.asList(
                new ConcatRowsExample.Person(1, "Alice"),
                new ConcatRowsExample.Person(2, "Bob")
            ),
            ConcatRowsExample.Person.class
        );

        df2 = spark.createDataFrame(
            Arrays.asList(
                new ConcatRowsExample.Person(3, "Charlie"),
                new ConcatRowsExample.Person(4, "Diana")
            ),
            ConcatRowsExample.Person.class
        );
    }

    @AfterAll
    static void tearDownClass() {
        spark.stop();
    }

    @Test
    void givenTwoDataFrames_whenConcatenated_thenRowCountMatches() {
        Dataset<Row> combined = df1
            .unionByName(df2);

        assertEquals(
            4,
            combined.count(),
            "The combined DataFrame should have 4 rows"
        );
    }

    @Test
    void givenTwoDataFrames_whenConcatenated_thenSchemaRemainsSame() {
        Dataset<Row> combined = df1
            .unionByName(df2);

        assertEquals(
            df1.schema(),
            combined.schema(),
            "Schema should remain consistent after concatenation"
        );
    }

    @Test
    void givenTwoDataFrames_whenConcatenated_thenDataContainsExpectedName() {
        Dataset<Row> combined = df1
            .unionByName(df2);

        assertTrue(
            combined
                .filter("name = 'Charlie'")
                .count() > 0,
            "Combined DataFrame should contain Charlie"
        );
    }
}

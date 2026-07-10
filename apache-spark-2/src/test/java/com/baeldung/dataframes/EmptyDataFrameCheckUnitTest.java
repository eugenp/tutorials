package com.baeldung.dataframes;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.apache.spark.sql.functions.col;

public class EmptyDataFrameCheckUnitTest {
    private static SparkSession spark;

    @BeforeAll
    static void setUp() {
        spark = EmptyDataFrameCheck.createSession();
    }

    @AfterAll
    static void tearDown() {
        if (spark != null) {
            spark.stop();
        }
    }

    @Test
    void givenEmptyDataFrame_whenUsingIsEmpty_thenReturnTrue() {
        Dataset<Row> allPlayers = EmptyDataFrameCheck.getDataFrame(spark);
        Dataset<Row> englandPlayers = allPlayers.filter(col("country").equalTo("England"));
        Assertions.assertTrue(englandPlayers.isEmpty());
    }

    @Test
    void givenNonEmptyDataFrame_whenUsingIsEmpty_thenReturnFalse() {
        Dataset<Row> allPlayers = EmptyDataFrameCheck.getDataFrame(spark);
        Assertions.assertFalse(allPlayers.isEmpty());
    }

    @Test
    void givenEmptyDataFrame_whenUsingCount_thenReturnZero() {
        Dataset<Row> allPlayers = EmptyDataFrameCheck.getDataFrame(spark);
        Dataset<Row> englandPlayers = allPlayers.filter(col("country").equalTo("England"));
        Assertions.assertEquals(0, englandPlayers.count());
    }

    @Test
    void givenNonEmptyDataFrame_whenUsingCount_thenReturnNonZero() {
        Dataset<Row> allPlayers = EmptyDataFrameCheck.getDataFrame(spark);
        Assertions.assertNotEquals(0, allPlayers.count());
    }

    @Test
    void givenEmptyDataFrame_whenUsingTakeAsList_thenReturnZero() {
        Dataset<Row> allPlayers = EmptyDataFrameCheck.getDataFrame(spark);
        Dataset<Row> englandPlayers = allPlayers.filter(col("country").equalTo("England"));
        Assertions.assertEquals(0, englandPlayers.takeAsList(1).size());
    }

    @Test
    void givenNonEmptyDataFrame_whenUsingTakeAsList_thenReturnNonZero() {
        Dataset<Row> allPlayers = EmptyDataFrameCheck.getDataFrame(spark);
        Assertions.assertNotEquals(0, allPlayers.takeAsList(1).size());
    }

    @Test
    void givenEmptyDataFrame_whenUsingTakeAsList_thenReturnEmptyList() {
        Dataset<Row> allPlayers = EmptyDataFrameCheck.getDataFrame(spark);
        Dataset<Row> englandPlayers = allPlayers.filter(col("country").equalTo("England"));
        Assertions.assertTrue(englandPlayers.takeAsList(1).isEmpty());
    }

    @Test
    void givenNonEmptyDataFrame_whenUsingTakeAsList_thenReturnEmptyList() {
        Dataset<Row> allPlayers = EmptyDataFrameCheck.getDataFrame(spark);
        Assertions.assertFalse(allPlayers.takeAsList(1).isEmpty());
    }
}

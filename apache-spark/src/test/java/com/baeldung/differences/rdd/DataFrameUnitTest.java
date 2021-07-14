package com.baeldung.differences.rdd;

import static org.apache.spark.sql.functions.col;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataFrameUnitTest {
    private static SparkSession session;
    private static Dataset<Row> data;

    @BeforeClass
    public static void init() {
        session = SparkSession.builder()
            .appName("TouristDataFrameExample")
            .master("local[*]")
            .getOrCreate();
        DataFrameReader dataFrameReader = session.read();
        data = dataFrameReader.option("header", "true")
            .csv("data/Tourist.csv");
    }

    @AfterClass
    public static void cleanup() {
        session.stop();
    }

    @Test
    public void whenSelectSpecificColumns_thenColumnsFiltered() {
        Dataset<Row> selectedData = data.select(col("country"), col("year"), col("value"));
        selectedData.show();
        
        List<String> resultList = Arrays.asList(selectedData.columns());
        assertTrue(resultList.contains("country"));
        assertTrue(resultList.contains("year"));
        assertTrue(resultList.contains("value"));
        assertFalse(resultList.contains("Series"));

    }

    @Test
    public void whenFilteringByCountry_thenCountryRecordsSelected() {
        Dataset<Row> filteredData = data.filter(col("country").equalTo("Mexico"));
        filteredData.show();
        
        filteredData.foreach(record -> {
            assertEquals("Mexico", record.get(1));
        });
        
    }

    @Test
    public void whenGroupCountByCountry_thenContryTotalRecords() {
        Dataset<Row> recordsPerCountry = data.groupBy(col("country"))
            .count();
        recordsPerCountry.show();
        
        Dataset<Row> filteredData = recordsPerCountry.filter(col("country").equalTo("Sweden"));
        assertEquals(new Long(12), filteredData.first()
            .get(1));
    }

}

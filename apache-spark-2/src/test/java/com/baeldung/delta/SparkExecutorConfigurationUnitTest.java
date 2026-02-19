package com.baeldung.delta;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SparkExecutorConfigurationUnitTest {

    private static SparkSession spark;

    @BeforeAll
    public static void setUp() {
        spark = SparkSession.builder()
                .appName("StaticExecutorAllocationExample")
                .config("spark.executor.instances", "8")
                .config("spark.executor.cores", "4")
                .config("spark.executor.memory", "8G")
                .master("local[*]")
                .getOrCreate();
    }

    @AfterAll
    public static void tearDown() {
        if (spark != null) {
            spark.stop();
        }
    }

    @Test
    public void givenExecutor_whenUsingStaticAllocation_thenPrintAndValidate() {
        SparkConf conf = spark.sparkContext().getConf();

        assertEquals("8", conf.get("spark.executor.instances"));
        assertEquals("4", conf.get("spark.executor.cores"));
        assertEquals("8G", conf.get("spark.executor.memory"));
        assertEquals("StaticExecutorAllocationExample", conf.get("spark.app.name"));
    }
}

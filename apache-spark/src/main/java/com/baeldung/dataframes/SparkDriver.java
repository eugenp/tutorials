package com.baeldung.dataframes;

import java.io.Serializable;

import org.apache.spark.sql.SparkSession;

public class SparkDriver implements Serializable {

    public static SparkSession getSparkSession() {
        return SparkSession.builder()
            .appName("Customer Aggregation pipeline")
            .master("local")
            .getOrCreate();

    }
}

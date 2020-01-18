package com.baeldung.dataframes;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import static org.apache.spark.sql.functions.col;

public class DataframesExperiments {

    private static final Logger LOGGER = Logger.getLogger(DataframesExperiments.class);

    public static void main(String[] args) {
        Logger.getLogger("org").setLevel(Level.OFF);

        SparkSession session = SparkSession
            .builder()
            .master("local[2]")
            .appName("Java Spark Dataframes example")
            .getOrCreate();

        Dataset<Row> df = session.read().json("{SPARK_HOME}/examples/src/main/resources/people.json");
        df.show();

        df.select("name").show();

        df.select(col("name"), col("age").plus(1)).show();

        df.filter(col("age").gt(25)).show();

        df.groupBy("age").count().show();

        df.createOrReplaceTempView("people");

        Dataset<Row> sqlDF = session.sql("SELECT * FROM people");

        sqlDF.show();
    }
}

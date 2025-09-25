package com.baeldung.spark.dataframeconcat;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class ConcatRowsExample {

    private static final Logger logger = LoggerFactory.getLogger(ConcatRowsExample.class);

    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder()
            .appName("Row-wise Concatenation Example")
            .master("local[*]")
            .getOrCreate();

        try {
            // First DataFrame
            Dataset<Row> df1 = spark.createDataFrame(
                Arrays.asList(
                    new Person(1, "Alice"),
                    new Person(2, "Bob")
                ),
                Person.class
            );

            // Second DataFrame
            Dataset<Row> df2 = spark.createDataFrame(
                Arrays.asList(
                    new Person(3, "Charlie"),
                    new Person(4, "Diana")
                ),
                Person.class
            );

            logger.info("First DataFrame:");
            df1.show();

            logger.info("Second DataFrame:");
            df2.show();

            // Row-wise concatenation (union) with method chaining and 4-space indents
            Dataset<Row> combined = df1
                .unionByName(df2);

            logger.info("After row-wise concatenation:");
            combined.show();
        } finally {
            spark.stop();
        }
    }

    public static class Person implements java.io.Serializable {
        private int id;
        private String name;

        public Person() {
        }

        public Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

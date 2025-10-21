package com.baeldung.delta;

import org.apache.spark.sql.*;
import java.io.Serializable;
import java.nio.file.Files;

public class DeltaLake {
    public static SparkSession createSession() {
        return SparkSession.builder()
                .appName("DeltaLake")
                .master("local[*]")
                .config("spark.sql.extensions", "io.delta.sql.DeltaSparkSessionExtension")
                .config("spark.sql.catalog.spark_catalog", "org.apache.spark.sql.delta.catalog.DeltaCatalog")
                .getOrCreate();
    }

    public static String preparePeopleTable(SparkSession spark) {
        try {
            String tablePath = Files.createTempDirectory("delta-table-").toAbsolutePath().toString();

            Dataset<Row> data = spark.createDataFrame(
                    java.util.Arrays.asList(
                            new Person(1, "Alice"),
                            new Person(2, "Bob")
                    ),
                    Person.class
            );

            data.write().format("delta").mode("overwrite").save(tablePath);
            spark.sql("DROP TABLE IF EXISTS people");
            spark.sql("CREATE TABLE IF NOT EXISTS people USING DELTA LOCATION '" + tablePath + "'");
            return tablePath;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void cleanupPeopleTable(SparkSession spark) {
        spark.sql("DROP TABLE IF EXISTS people");
    }

    public static void stopSession(SparkSession spark) {
        if (spark != null) {
            spark.stop();
        }
    }

    public static class Person implements Serializable {
        private int id;
        private String name;

        public Person() {}
        public Person(int id, String name) { this.id = id; this.name = name; }

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}

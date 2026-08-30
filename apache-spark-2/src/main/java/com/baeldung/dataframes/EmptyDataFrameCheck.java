package com.baeldung.dataframes;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.util.List;

public class EmptyDataFrameCheck {

    private static final StructType PLAYER_SCHEMA = DataTypes.createStructType(new StructField[] {
            DataTypes.createStructField("id", DataTypes.IntegerType, false),
            DataTypes.createStructField("name", DataTypes.StringType, false),
            DataTypes.createStructField("country", DataTypes.StringType, false)
    });

    public static SparkSession createSession() {
        return SparkSession.builder()
                .appName("Spark")
                .master("local[*]")
                .getOrCreate();
    }

    public static Dataset<Row> getDataFrame(SparkSession spark) {
        List<Row> players = List.of(
                RowFactory.create(1, "Messi", "Argentina"),
                RowFactory.create(2, "Ronaldo", "Portugal"),
                RowFactory.create(3, "Mbappe", "France"));
        return spark.createDataFrame(players, PLAYER_SCHEMA);
    }
}
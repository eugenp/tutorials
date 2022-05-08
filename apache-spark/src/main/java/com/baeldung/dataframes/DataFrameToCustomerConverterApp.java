package com.baeldung.dataframes;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;

public class DataFrameToCustomerConverterApp {

    public static void main(String[] args) {
        Dataset<Row> df = SparkDriver.getSparkSession()
            .read()
            .format("org.apache.spark.sql.execution.datasources.json.JsonFileFormat")
            .option("multiline", true)
            .load("data/minCustomerData.json");
        df.show();
        df.printSchema();
        Dataset<Customer> customerDS = df.map(new RowToCustomerMapper(), Encoders.bean(Customer. class));
        customerDS.show();
        customerDS.printSchema();
    }
}

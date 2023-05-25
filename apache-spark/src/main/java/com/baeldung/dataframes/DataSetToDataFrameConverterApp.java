package com.baeldung.dataframes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class DataSetToDataFrameConverterApp {

    private static final SparkSession SPARK_SESSION = SparkDriver.getSparkSession();

    public static void main(String[] args) {

        Dataset<Customer> customerDataset = convertToDataSetFromPOJO();
        Dataset<Row> customerDataFrame = customerDataset.toDF();
        print(customerDataFrame);

        List<String> names = getNames();
        Dataset<String> namesDataset = convertToDataSetFromStrings(names);
        Dataset<Row> namesDataFrame = namesDataset.toDF();
        print(namesDataFrame);
    }

    private static Dataset<String> convertToDataSetFromStrings(List<String> names) {
        return SPARK_SESSION.createDataset(names, Encoders.STRING());
    }

    private static Dataset<Customer> convertToDataSetFromPOJO() {
        return SPARK_SESSION.createDataset(CUSTOMERS, Encoders.bean(Customer.class));
    }

    private static final List<Customer> CUSTOMERS = Arrays.asList(
        aCustomerWith("01", "jo", "Female", 2000),
        aCustomerWith("02", "jack", "Female", 1200),
        aCustomerWith("03", "ash", "male", 2000),
        aCustomerWith("04", "emma", "Female", 2000)
    );

    private static List<String> getNames() {
        return CUSTOMERS.stream()
            .map(Customer::getName)
            .collect(Collectors.toList());
    }

    private static void print(Dataset<Row> df) {
        df.show();
        df.printSchema();
    }

    private static Customer aCustomerWith(String id, String name, String gender, int amount) {
        return new Customer(id, name, gender, amount);
    }
}

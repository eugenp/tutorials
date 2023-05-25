package com.baeldung.dataframes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class CustomerToDataFrameConverterApp {

    private static final List<Customer>  CUSTOMERS = Arrays.asList(
        aCustomerWith("01", "jo", "Female", 2000),
        aCustomerWith("02", "jack", "Male", 1200)
    );

    public static void main(String[] args) {
        Dataset<Row> dataFrame = convertAfterMappingRows(CUSTOMERS);
        print(dataFrame);
        Dataset<Row> customerDF = convertToDataFrameWithNoChange();
        print(customerDF);
    }

    public static Dataset<Row> convertToDataFrameWithNoChange() {
       return SparkDriver.getSparkSession().createDataFrame(CUSTOMERS, Customer.class);
    }

    public static Dataset<Row> convertAfterMappingRows(List<Customer> customer) {
        List<Row> rows = customer.stream()
            .map(c -> new CustomerToRowMapper().call(c))
            .collect(Collectors.toList());

        return SparkDriver.getSparkSession()
            .createDataFrame(rows, SchemaFactory.minimumCustomerDataSchema());
    }

    private static Customer aCustomerWith(String id, String name, String gender, int amount) {
        return new Customer(id, name, gender, amount);
    }

    private static void print(Dataset<Row> dataFrame) {
        dataFrame.printSchema();
        dataFrame.show();
    }

}
